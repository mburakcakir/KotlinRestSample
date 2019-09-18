package com.mburakcakir.kotlinrestsample.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.mburakcakir.kotlinrestsample.R
import com.mburakcakir.kotlinrestsample.di.Constants
import com.mburakcakir.kotlinrestsample.di.DynamicConstants
import com.mburakcakir.kotlinrestsample.model.AddUserModel
import com.mburakcakir.kotlinrestsample.model.UserModel
import com.mburakcakir.kotlinrestsample.utils.Utils
import com.mburakcakir.kotlinrestsample.utils.UtilsEditUser
import com.mburakcakir.kotlinrestsample.utils.UtilsService
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException


class EditUserActivity : AppCompatActivity(),  View.OnClickListener {

    internal lateinit var btnEdit: Button
    internal lateinit var btnDelete: Button
    internal lateinit var btnBrowse: Button

    internal lateinit var etName: TextInputEditText
    internal lateinit var etSurname: TextInputEditText
    internal lateinit var etAge: TextInputEditText

    internal var spinnerGender: Spinner? = null
    internal var imgProfile: ImageView? = null

    internal var isDataHave: String = ""
    internal var sonuc : Int = R.array.array_gender
    internal var userImageString: String = ""
    internal lateinit var imageString : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


        initComponents()
        initListeners()

        isDataHave = intent.getStringExtra("isDataHave")

        baseDataControl()
        setSpinnerData()

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnBrowse -> UtilsEditUser.openGallery(this@EditUserActivity)
            R.id.btnEdit -> controlDataUpdate()
            R.id.btnDelete -> UtilsService.deleteOneUser(DynamicConstants.USER_MODEL!!.id)

        }
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

    fun initListeners() {
        btnBrowse.setOnClickListener(this)
        btnEdit.setOnClickListener(this)
        btnDelete.setOnClickListener(this)
    }

    fun baseDataControl() {
        if (isDataHave.equals("yes")) {
            Toast.makeText(applicationContext, "Bilgiler Yüklendi.", Toast.LENGTH_LONG).show()
            bindUserData()
            checkUserGender()
        }

        if (isDataHave.equals("no")) {
            Toast.makeText(applicationContext, "Yeni Kayıt Ekranı Oluşturuldu", Toast.LENGTH_LONG).show()
        }
    }

    fun setSpinnerData() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            sonuc, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender?.setAdapter(adapter)

    }

    fun checkUserGender(){
        if(DynamicConstants.USER_MODEL!!.gender == "Erkek") {
            sonuc = R.array.gender_man
        }
        else if(DynamicConstants.USER_MODEL!!.gender == "Kadın") {
            sonuc = R.array.gender_woman
        }
    }

    fun controlDataUpdate() {
        if(isDataHave.equals("yes"))  {
            updateUserData()
            Toast.makeText(this@EditUserActivity,"Düzenleme tamamlandı.",Toast.LENGTH_SHORT).show()
        }
        if(isDataHave.equals("no")) {
            addNewUser()
            Toast.makeText(this@EditUserActivity,"Yeni kayıt eklendi.",Toast.LENGTH_SHORT).show()
        }
    }

    fun updateUserData() {

        val userModel= UserModel(
            Integer.parseInt(etAge.text.toString()),
            spinnerGender?.selectedItem.toString(),
            DynamicConstants.USER_MODEL!!.id,
            etName.text.toString(),
            isImageEmptyUser(),
            "null",
            etSurname.text.toString())

            UtilsService.updateUser(userModel)
    }

    fun addNewUser() {

        val userModel= AddUserModel(
            etName.text.toString(),
            etSurname.text.toString(),
            Integer.parseInt(etAge.text.toString()),
            spinnerGender?.selectedItem.toString(),
            isImageEmptyRegister()
        )

       UtilsService.addUser(userModel)
    }

    fun bindUserData() {
        etName.setText(DynamicConstants.USER_MODEL!!.name)
        etSurname.setText(DynamicConstants.USER_MODEL!!.surname.toUpperCase())
        etAge.setText(DynamicConstants.USER_MODEL!!.age.toString())
        imgProfile?.setImageBitmap(Utils.getBitmapByString(DynamicConstants.USER_MODEL!!.profileImage))
    }



    fun isImageEmptyUser() : String {
        if(userImageString == "")
            imageString = DynamicConstants.USER_MODEL!!.profileImage
        else
            imageString = userImageString
        return imageString
    }

    fun isImageEmptyRegister() : String {
        if(userImageString == "")
            imageString = DynamicConstants.baseUrl
        else
            imageString = userImageString
        return imageString
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            Constants.PERMISSION_CODE -> {
                if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    UtilsEditUser.pickImageFromGallery(this@EditUserActivity)
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
                userImageString = temp
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

}




