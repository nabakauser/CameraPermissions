package com.example.camerapermissions

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.camerapermissions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        //shdshowRequestPermRationale -> returns true if the user has denied the permission previously
        //                            -> returns false if permission is denied and dont show again is activated
        //                            -> default value = false for sdk > 23
        if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                Manifest.permission.CAMERA)) {
                    requestPermission()
        }
        else {
            requestPermission()
            }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == // [0] -> index of first permission in arrayOf(requestPermission())
                    PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                        capturePhoto()
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this@MainActivity,
            arrayOf(Manifest.permission.CAMERA), 1)
    }

    private fun capturePhoto() {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 1234)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1234 && data != null){
            binding?.uiIvDisplayImage?.setImageBitmap(data.extras?.get("data") as Bitmap)
        }
    }
}