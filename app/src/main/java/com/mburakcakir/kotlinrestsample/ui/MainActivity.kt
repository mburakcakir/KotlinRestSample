package com.mburakcakir.kotlinrestsample.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.mburakcakir.kotlinrestsample.R
import com.mburakcakir.kotlinrestsample.utils.Utils
import com.mburakcakir.kotlinrestsample.utils.UtilsService
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    internal lateinit var btnAdd : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.rcvUsers)
        btnAdd = findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener {

            Utils.openActivity(this@MainActivity)
        }


        refreshLayout.setOnRefreshListener {

            UtilsService.getAllUsers(refreshLayout, recyclerView, applicationContext, this@MainActivity)
        }

        UtilsService.getAllUsers(refreshLayout, recyclerView, applicationContext, this@MainActivity)

    }
}
