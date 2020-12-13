package com.stu.syllabuskt.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.stu.syllabuskt.personal.theme.ThemeUtil

abstract class BaseActivity : AppCompatActivity() {

    private val handler = Handler()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setTheme(ThemeUtil.getStyle())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(ThemeUtil.getStyle())
        setContentView(getContentView())
        init()
    }

    abstract fun getContentView(): Int

    open fun init() {}

    fun runOnUiThread(action: () -> Unit) {
        handler.post(action)
    }

}
