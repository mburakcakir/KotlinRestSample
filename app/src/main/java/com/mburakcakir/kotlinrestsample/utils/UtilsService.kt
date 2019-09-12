package com.mburakcakir.kotlinrestsample.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.mburakcakir.kotlinrestsample.model.AddUserModel
import com.mburakcakir.kotlinrestsample.model.UserModel
import com.mburakcakir.kotlinrestsample.networking.ServiceApiClient
import com.mburakcakir.kotlinrestsample.ui.UserAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UtilsService {

    companion object {


        val serviceClient by lazy {
            ServiceApiClient.create()
        }


        var disposable: Disposable? = null

        fun getAllUsers(refreshLayout : androidx.swiperefreshlayout.widget.SwipeRefreshLayout, recyclerView : androidx.recyclerview.widget.RecyclerView, context : Context, activity: Activity) {
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

        fun deleteOneUser(id: Int, activity: Activity) {

            disposable = serviceClient.deleteUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> Log.v("USER ID ${id}: ", "" + result)
                        Toast.makeText(activity,"Kullanıcı silindi.", Toast.LENGTH_SHORT).show()},
                    { error -> Log.e("ERROR", error.message) }
                )

        }

        fun bindToRecycleview(userList:List<UserModel>, recyclerView : androidx.recyclerview.widget.RecyclerView, context : Context, activity: Activity)
        {

            recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                context,
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
            var recyclerViewAdapter = UserAdapter(userList,activity)
            recyclerView.adapter = recyclerViewAdapter
        }
    }
}