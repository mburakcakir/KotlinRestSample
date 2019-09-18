package com.mburakcakir.kotlinrestsample.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.ImageView
import android.widget.Toast
import com.mburakcakir.kotlinrestsample.di.Constants

class UtilsEditUser {

    companion object {

        fun pickImageFromGallery(activity: Activity) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activity.startActivityForResult(intent, Constants.IMAGE_PICK_CODE)
        }

        fun openGallery(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    activity.requestPermissions(permissions, Constants.PERMISSION_CODE)
                } else {
                    pickImageFromGallery(activity)
                }
            } else {
                pickImageFromGallery(activity)
            }
        }

    }
}