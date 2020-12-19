package com.stu.syllabuskt.personal.setting

import android.content.Intent

/**
 * Create by yuan on 2020/12/17
 */
fun SettingActivity.startAboutUs() {
    startActivity(Intent(this, AboutUsActivity::class.java))
}

fun SettingActivity.startFeedBack() {
    startActivity(Intent(this, FeedbackActivity::class.java))
}