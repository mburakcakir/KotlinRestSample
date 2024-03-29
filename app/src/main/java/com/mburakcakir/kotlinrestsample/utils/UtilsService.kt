package com.mburakcakir.kotlinrestsample.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mburakcakir.kotlinrestsample.model.AddUserModel
import com.mburakcakir.kotlinrestsample.model.UserModel
import com.mburakcakir.kotlinrestsample.networking.ServiceApiClient
import com.mburakcakir.kotlinrestsample.ui.UserAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user.*

class UtilsService {

    companion object {

        internal lateinit var btnRetry : Button
        internal lateinit var layoutError : LinearLayout
        internal lateinit var refreshLayout : SwipeRefreshLayout
        internal lateinit var recyclerView : RecyclerView
        internal lateinit var context : Context
        internal lateinit var activity: Activity
        internal var connectionControl : Boolean = false


        val serviceClient by lazy {
            ServiceApiClient.create()
        }

        var disposable: Disposable? = null

        fun loadErrorComponents(btnRetry : Button, layoutError : LinearLayout) {
            this.btnRetry = btnRetry
            this.layoutError = layoutError
        }

        fun loadServiceComponents(refreshLayout : androidx.swiperefreshlayout.widget.SwipeRefreshLayout, recyclerView : androidx.recyclerview.widget.RecyclerView, context : Context, activity: Activity) {
            this.refreshLayout = refreshLayout
            this.recyclerView = recyclerView
            this.context = context
            this.activity = activity
        }

        fun  checkErrorMessage() {

            layoutError.visibility = View.VISIBLE
            btnRetry.setOnClickListener {
                if(!connectionControl)
                    getAllUsers()
            }
        }

        fun getAllUsers() {
            refreshLayout.isRefreshing = true
            disposable = serviceClient.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                            result -> Log.v("USERS", "" + result)
                        bindToRecycleview(result)
                        internetConnectionProcess()


                    },
                    { error -> Log.e("ERROR", error.message)
                        noInternetConnectionProcess()


                    }
                )
        }

        fun noInternetConnectionProcess() {
            refreshLayout.isRefreshing = false
            layoutError.visibility = View.VISIBLE
            Toast.makeText(activity,"İnternet Bağlantınızı Kontrol Ediniz.",Toast.LENGTH_SHORT).show()
            checkErrorMessage()
        }

        fun internetConnectionProcess() {
            refreshLayout.isRefreshing = false
            layoutError.visibility = View.INVISIBLE
            Toast.makeText(context,"Bilgiler Yüklendi.",Toast.LENGTH_SHORT).show()
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
                    { result -> Log.v("USER ID ${id}: ", "" + result)
                        Toast.makeText(activity,"Kullanıcı silindi.", Toast.LENGTH_SHORT).show()},
                    { error -> Log.e("ERROR", error.message) }
                )

        }

        fun bindToRecycleview(userList:List<UserModel>)
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