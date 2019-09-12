package com.mburakcakir.kotlinrestsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mburakcakir.kotlinrestsample.R
import com.mburakcakir.kotlinrestsample.utils.Utils
import com.mburakcakir.kotlinrestsample.utils.UtilsService
import kotlinx.android.synthetic.main.activity_user.*


class UserActivity : AppCompatActivity() {

    internal lateinit var btnAdd : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rcvUsers)
        btnAdd = findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener {

            Utils.openActivity(this@UserActivity)
        }

        refreshLayout.setOnRefreshListener {

            UtilsService.getAllUsers(refreshLayout, recyclerView, applicationContext, this@UserActivity)
        }

        UtilsService.getAllUsers(refreshLayout, recyclerView, applicationContext, this@UserActivity)

    }
}
