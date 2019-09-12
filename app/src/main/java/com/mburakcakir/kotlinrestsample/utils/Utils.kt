package com.mburakcakir.kotlinrestsample.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import com.mburakcakir.kotlinrestsample.di.DynamicConstants
import com.mburakcakir.kotlinrestsample.model.UserModel
import com.mburakcakir.kotlinrestsample.ui.EditUserActivity
import kotlin.random.Random

class Utils {

    companion object{

        internal fun getBitmapByString(byteCode: String) : Bitmap {

            val decodedBytes = Base64.decode(byteCode, Base64.DEFAULT)
            val decodedByte2 = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

            return decodedByte2!!
        }



        internal fun generateRandomTime() : String {

            var randomHour:String =   Random.nextInt(0, 23).toString()
            var  randomMinute =   Random.nextInt(0, 59).toString()

            if(randomHour.length == 1  )
                randomHour = "0" + randomHour

            if(randomMinute.length == 1  )
                randomMinute = "0" + randomMinute


            return randomHour + ":" + randomMinute
        }



        internal fun openActivity(activity: Activity,userModel: UserModel)  {

            val toEditActivity = Intent(activity, EditUserActivity::class.java)
                toEditActivity.putExtra("isDataHave","yes")
                DynamicConstants.USER_MODEL=userModel
            activity.startActivity(toEditActivity)

        }

        internal fun openActivity(activity: Activity)  {
            val toEditActivity = Intent(activity, EditUserActivity::class.java)
            toEditActivity.putExtra("isDataHave","no")
            activity.startActivity(toEditActivity)

        }


    }
}