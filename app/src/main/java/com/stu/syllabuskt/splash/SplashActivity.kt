package com.stu.syllabuskt.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stu.syllabuskt.StuContext

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dealSplashBusiness()
    }

    private fun dealSplashBusiness() {
        Thread.sleep(1000)
        if (StuContext.getDBService().getUserAccount(this@SplashActivity).isNullOrEmpty()
            || StuContext.getDBService().getUserPassword(this@SplashActivity).isNullOrEmpty())
            toLoginView()
        else toMainView()
    }
}
