package com.mburakcakir.kotlinrestsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.mburakcakir.kotlinrestsample.R
import com.mburakcakir.kotlinrestsample.utils.Utils
import com.mburakcakir.kotlinrestsample.utils.UtilsService
import kotlinx.android.synthetic.main.activity_user.*


class UserActivity : AppCompatActivity() {

    internal lateinit var btnAdd : Button
    internal var imgError: ImageView? = null
    internal lateinit var layoutError : LinearLayout
    internal lateinit var btnRetry: Button
    internal lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        recyclerView = findViewById<RecyclerView>(R.id.rcvUsers)
        btnAdd = findViewById(R.id.btnAdd)
        imgError = findViewById(R.id.imgError)
        layoutError = findViewById(R.id.layoutError)
        btnRetry = findViewById(R.id.btnRetry)

        layoutError.visibility = View.GONE

        UtilsService.loadErrorComponents(btnRetry,layoutError)
        UtilsService.loadServiceComponents(refreshLayout, recyclerView, applicationContext, this@UserActivity)

        btnAdd.setOnClickListener {
            Utils.openActivity(this@UserActivity)
        }

        refreshLayout.setOnRefreshListener {
            UtilsService.getAllUsers()
        }

        UtilsService.getAllUsers()

    }



}
