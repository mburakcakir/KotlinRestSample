package com.mburakcakir.kotlinrestsample.utils

import android.app.Activity
import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import com.mburakcakir.kotlinrestsample.R
import com.mburakcakir.kotlinrestsample.model.AddUserModel
import com.mburakcakir.kotlinrestsample.model.UserModel
import com.mburakcakir.kotlinrestsample.networking.ServiceApiClient
import com.mburakcakir.kotlinrestsample.ui.MainActivity
import com.mburakcakir.kotlinrestsample.ui.UserAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class UtilsService {

    companion object {


        val serviceClient by lazy {
            ServiceApiClient.create()
        }


        var disposable: Disposable? = null

        fun getAllUsers(refreshLayout : SwipeRefreshLayout,recyclerView : RecyclerView,context : Context, activity: Activity) {
            refreshLayout.isRefreshing = true
            disposable = serviceClient.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                            result -> Log.v("USERS", "" + result)
                        bindToRecycleview(result,recyclerView,context,activity)
                        refreshLayout.isRefreshing = false

                    },
                    { error -> Log.e("ERROR", error.message)
                        refreshLayout.isRefreshing = false;
                    }
                )
        }

        fun addUser(user: AddUserModel) {

            disposable = serviceClient.addUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> Log.v("POSTED USER", "" + user) },
                    { error -> Log.e("ERROR", error.message) }
                )
        }

        fun updateUser(user: UserModel) {

            disposable = serviceClient.updateUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> Log.v("UPDATED USER", "" + user) },
                    { error -> Log.e("ERROR", error.message) }
                )
        }

        fun deleteOneUser(id: Int) {

            disposable = serviceClient.deleteUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> Log.v("USER ID ${id}: ", "" + result) },
                    { error -> Log.e("ERROR", error.message) }
                )

        }

        fun bindToRecycleview(userList:List<UserModel>,recyclerView : RecyclerView,context : Context, activity: Activity)
        {

            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            var recyclerViewAdapter = UserAdapter(userList,activity)
            recyclerView.adapter = recyclerViewAdapter
        }
    }
}