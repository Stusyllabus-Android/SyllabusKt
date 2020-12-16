package com.stu.syllabuskt.personal.setting

import android.content.Intent
import android.view.View
import android.widget.RelativeLayout
import com.stu.syllabuskt.R
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.base.BaseActivity

class SettingActivity : BaseActivity(), View.OnClickListener {

    override fun init() {
        super.init()
        findViewById<RelativeLayout>(R.id.helpAndFeedback).setOnClickListener(this)
        findViewById<RelativeLayout>(R.id.share2).setOnClickListener(this)
        findViewById<RelativeLayout>(R.id.aboutUs).setOnClickListener(this)
        findViewById<RelativeLayout>(R.id.logout).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
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
