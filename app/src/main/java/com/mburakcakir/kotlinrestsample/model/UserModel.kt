package com.mburakcakir.kotlinrestsample.model
import com.google.gson.annotations.SerializedName

data class AddUserModel(

    @SerializedName("name")
    var name: String,
    @SerializedName("surname")
    var surname: String,

    @SerializedName("age")
    var age: Int,
    @SerializedName("gender")
    var gender: String,

    @SerializedName("profileImage")
    var profileImage: String

)

data class UserModel(

    @SerializedName("age")
    var age: Int,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("profileImage")
    var profileImage: String,
    @SerializedName("profileImageUrl")
   var profileImageUrl: String,
    @SerializedName("surname")
    var surname: String

)