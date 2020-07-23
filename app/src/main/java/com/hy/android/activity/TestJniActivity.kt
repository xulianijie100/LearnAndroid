package com.hy.android.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.hy.android.R
import com.hy.android.utils.Logger
import com.hy.learn.ICallback
import com.hy.learn.TestJni
import com.hy.learn.UserBean
import kotlinx.android.synthetic.main.activity_jni.*

class TestJniActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jni)
        initToolbar()
        initView()
    }

    private fun initView() {
        Logger.d("TestJni  -----", TestJni.stringFromJNI())

        //修改Java类的属性字段
        val bean = UserBean("java", 12)
        TestJni.accessField(bean)
        TestJni.accessStaticField(bean)
        Logger.d("改变属性字段 ---- ", bean.name + " : " + bean.age + " : " + UserBean.score)

        TestJni.callMethod(bean)
        TestJni.callStaticMethod(bean)

        TestJni.callbackMethod(object : ICallback {
            override fun onSuccess(msg: String?) {
                Logger.d("onSuccess  -----", msg)
            }

            override fun onFail(error: String?) {
                Logger.d("onFail   --------", error)
            }
        })

        TestJni.callbackChildThread(object : ICallback {
            override fun onSuccess(msg: String?) {
                Logger.d("onSuccess  -----", msg)
            }

            override fun onFail(error: String?) {
                Logger.d("onFail   --------", error)
            }
        })

        val user = TestJni.callConstructor()
        Logger.d("访问构造器  ---- ", user.name + " : " + user.age)


        btn_h264.setOnClickListener(this)
        btn_aac.setOnClickListener(this)
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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_h264 -> {
            }
            R.id.btn_aac -> {
            }
        }
    }
}