package com.mburakcakir.kotlinrestsample.networking


import com.mburakcakir.kotlinrestsample.di.Constants
import com.mburakcakir.kotlinrestsample.model.AddUserModel
import com.mburakcakir.kotlinrestsample.model.UserModel
import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.http.DELETE



interface ServiceApiClient {


    @GET("users")
    fun getUsers(): io.reactivex.Observable<List<UserModel>>

    @GET("users/{id}")
    fun getUserByID(@Path("id") id: Int): io.reactivex.Observable<UserModel>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("users/")
    fun addUser(@Body article: AddUserModel): io.reactivex.Observable<AddUserModel>

    @Headers("Content-Type: application/json;charset=utf-8")
    @PUT("users/")
    fun updateUser(@Body article: UserModel): io.reactivex.Observable<UserModel>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Int): io.reactivex.Observable<UserModel>



    companion object {

        fun create(): ServiceApiClient {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()

            return retrofit.create(ServiceApiClient::class.java)

        }
    }
}