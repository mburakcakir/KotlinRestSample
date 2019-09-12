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
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activity.startActivityForResult(intent, Constants.IMAGE_PICK_CODE)
        }


        fun isDataHave(isDataHave : String) {



        }

        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray,activity: Activity) {
            when(requestCode) {
                Constants.PERMISSION_CODE -> {
                    if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        pickImageFromGallery(activity)
                    }
                    else {


                    }
                }
            }
        }

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, imgProfile : ImageView) {
            if(resultCode == Activity.RESULT_OK && requestCode == Constants.IMAGE_PICK_CODE) {
                imgProfile?.setImageURI(data?.data)
            }
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