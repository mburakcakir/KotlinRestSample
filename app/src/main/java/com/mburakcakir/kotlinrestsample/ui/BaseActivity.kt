package com.mburakcakir.kotlinrestsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.mburakcakir.kotlinrestsample.R

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

    }

    fun initToolbar(toolbarId: Int) {
        val myToolbar = findViewById<View>(toolbarId) as Toolbar
        setSupportActionBar(myToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        assert(myToolbar != null)
        myToolbar.setLogo(R.mipmap.ic_launcher)
    }
}
