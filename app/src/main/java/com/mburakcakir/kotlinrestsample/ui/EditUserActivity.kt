package com.mburakcakir.kotlinrestsample.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.*
import com.mburakcakir.kotlinrestsample.R
import com.mburakcakir.kotlinrestsample.di.Constants
import com.mburakcakir.kotlinrestsample.di.DynamicConstants
import com.mburakcakir.kotlinrestsample.model.AddUserModel
import com.mburakcakir.kotlinrestsample.model.UserModel
import com.mburakcakir.kotlinrestsample.utils.Utils
import com.mburakcakir.kotlinrestsample.utils.UtilsService
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException


class EditUserActivity : AppCompatActivity(){

    internal lateinit var btnEdit: Button
    internal lateinit var btnDelete: Button
    internal lateinit var btnBrowse: Button
    internal lateinit var etName: EditText
    internal lateinit var etSurname: EditText
    internal lateinit var spinnerGender: Spinner
    internal lateinit var etAge: EditText
    internal var imgProfile: ImageView? = null
    internal val childrenPhotoString = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_userprofile)

        initComponents()

        val isDataHave = intent.getStringExtra("isDataHave")

        if (isDataHave.equals("yes")) {
            Toast.makeText(applicationContext, "YES", Toast.LENGTH_LONG).show()
            bindData();
        }

        if (isDataHave.equals("no")) {
            Toast.makeText(applicationContext, "NO", Toast.LENGTH_LONG).show()
        }

        btnBrowse.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, Constants.PERMISSION_CODE)
                } else {
                    pickImageFromGallery()
                }
            } else {
                pickImageFromGallery()
            }

        }

        btnEdit.setOnClickListener {

            if(isDataHave.equals("yes"))  {
                updateData()
                Toast.makeText(this@EditUserActivity,"Düzenleme tamamlandı.",Toast.LENGTH_SHORT).show()
            }
            if(isDataHave.equals("no")) {
                addData()
                Toast.makeText(this@EditUserActivity,"Yeni kayıt eklendi.",Toast.LENGTH_SHORT).show()
            }

        }

        btnDelete.setOnClickListener {
            UtilsService.deleteOneUser(DynamicConstants.USER_MODEL!!.id)
            Toast.makeText(this@EditUserActivity,"Kullanıcı silindi.",Toast.LENGTH_SHORT).show()
        }

    }

    internal fun updateData() {

        val userModel= UserModel(
            Integer.parseInt(etAge.text.toString()),
            "b",
            DynamicConstants.USER_MODEL!!.id,
            etName.text.toString(),
            DynamicConstants.USER_MODEL!!.profileImage,
            "null",
            etSurname.text.toString())


            UtilsService.updateUser(userModel)
    }

    internal fun addData() {

        val userModel= AddUserModel(
            etName.text.toString(),
            etSurname.text.toString(),
            Integer.parseInt(etAge.text.toString()),
            "male",
            DynamicConstants.USER_MODEL!!.profileImage
          )

       UtilsService.addUser(userModel)
    }



    fun bindData() {
        etName.setText(DynamicConstants.USER_MODEL!!.name)
        etSurname.setText(DynamicConstants.USER_MODEL!!.surname.toUpperCase())
        //spinnerGender.setText(DynamicConstants.USER_MODEL!!.gender)
        etAge.setText(DynamicConstants.USER_MODEL!!.age.toString())
       imgProfile?.setImageBitmap(Utils.getBitmapByString(DynamicConstants.USER_MODEL!!.profileImage))
    }

    fun initComponents() {
        btnEdit = findViewById(R.id.btnEdit)
        btnDelete = findViewById(R.id.btnDelete)
        btnBrowse = findViewById(R.id.btnBrowse)
        etName = findViewById(R.id.etName)
        etSurname = findViewById(R.id.etSurname)
        spinnerGender = findViewById(R.id.spinnerGender)
        etAge = findViewById(R.id.etAge)
        imgProfile = findViewById(R.id.imageView)
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                }
                else {

                    Toast.makeText(this,"Denied",Toast.LENGTH_SHORT).show()

                }
            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK)
        {
            val targetUri = data!!.getData()
            val bitmap:Bitmap
            try
            {
                val imageStream = this@EditUserActivity.getContentResolver().openInputStream(targetUri)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                bitmap = BitmapFactory.decodeStream(applicationContext.getContentResolver().openInputStream(targetUri))
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos)
                val b = baos.toByteArray()
                val temp = Base64.encodeToString(b, Base64.DEFAULT)
                val userImageString : String
                userImageString = "data:image/jpeg;base64," + temp
                imgProfile!!.setImageBitmap(selectedImage)
             //   imgProfile!!.setImageBitmap(Utils.getBitmapByString(userImageString))
                Log.d("userImageString", "parsing " + userImageString);

            }
            catch (e: FileNotFoundException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        }
        else
        {}

    }
    /*

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && requestCode == Constants.IMAGE_PICK_CODE) {
            imgProfile?.setImageURI(data?.data)

        }

    }

*/
}



