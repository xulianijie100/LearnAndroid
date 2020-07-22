package com.hy.android.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.hy.android.R
import com.hy.android.utils.Logger
import com.hy.learn.TestJni

class TestJniActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jni)
        initToolbar()
        initView()
    }

    private fun initView() {
        Logger.d("---- TestJni -----", TestJni.stringFromJNI())
        val testJni = TestJni()
        val user= testJni.invokeUserConstructor()
        Logger.d("---- TestJni -----",user.name+" : "+user.tel )
    }


    private fun initToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "JNI"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}