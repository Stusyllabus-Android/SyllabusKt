package com.stu.syllabuskt.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        init()
    }

    abstract fun getContentView(): Int

    open fun init() {}

}
