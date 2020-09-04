package com.stu.syllabuskt

import android.content.Intent
import com.stu.syllabuskt.login.LoginActivity
import com.stu.syllabuskt.splash.SplashActivity

/**
 *yuan
 *2020/9/4
 **/

fun SplashActivity.toLoginView() {
    startActivity(Intent(this, LoginActivity::class.java))
    this.finish()
}

fun SplashActivity.toMainView() {
    startActivity(Intent(this, MainActivity::class.java))
    this.finish()
}