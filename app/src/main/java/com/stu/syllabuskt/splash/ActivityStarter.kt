package com.stu.syllabuskt.splash

import android.content.Intent
import com.stu.syllabuskt.login.LoginActivity
import com.stu.syllabuskt.main.MainActivity

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

fun LoginActivity.toMainViewAct() {
    startActivity(Intent(this, MainActivity::class.java))
    this.finish()
}