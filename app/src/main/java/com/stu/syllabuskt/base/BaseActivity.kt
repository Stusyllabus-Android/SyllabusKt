package com.stu.syllabuskt.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message

abstract class BaseActivity : AppCompatActivity() {

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        init()
    }

    abstract fun getContentView(): Int

    open fun init() {}

    fun runOnUiThread(action: () -> Unit) {
        handler.post(action)
    }

}
