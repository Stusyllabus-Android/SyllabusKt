package com.stu.syllabuskt.personal.setting

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.stu.syllabuskt.App
import com.stu.syllabuskt.R
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.base.BaseActivity

class SettingActivity : BaseActivity(), View.OnClickListener {

    lateinit var backIcon: ImageView

    override fun init() {
        super.init()
        backIcon = findViewById<ImageView>(R.id.backIcon).apply {
            setOnClickListener { finish() }
            visibility = View.VISIBLE
        }
        findViewById<TextView>(R.id.titleBarTV).text = "设置"
        findViewById<RelativeLayout>(R.id.helpAndFeedback).setOnClickListener(this)
        findViewById<RelativeLayout>(R.id.share2).setOnClickListener(this)
        findViewById<RelativeLayout>(R.id.aboutUs).setOnClickListener(this)
        findViewById<TextView>(R.id.versionNameTextView).text =  "v${App.versionName}"
        findViewById<RelativeLayout>(R.id.logout).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.aboutUs -> startAboutUs()
            R.id.logout -> logout()
        }
    }

    private fun logout() {
        StuContext.getSharePreferences(this).edit().clear().apply()
        StuContext.getDBService().clearAllData(this)
        setResult(1000, Intent().putExtra("finishMainActivity", "finishMainActivity"))
        finish()
    }

    override fun getContentView(): Int {
        return R.layout.activity_setting
    }

}
