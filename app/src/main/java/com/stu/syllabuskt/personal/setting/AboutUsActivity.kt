package com.stu.syllabuskt.personal.setting

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseActivity

class AboutUsActivity : BaseActivity() {

    override fun getContentView(): Int {
        return R.layout.activity_about_us
    }

    override fun init() {
        super.init()
        findViewById<TextView>(R.id.titleBarTV).apply { text = "关于我们" }
        findViewById<ImageView>(R.id.backIcon).apply {
            visibility = View.VISIBLE
            setOnClickListener { finish() }
        }
    }
}