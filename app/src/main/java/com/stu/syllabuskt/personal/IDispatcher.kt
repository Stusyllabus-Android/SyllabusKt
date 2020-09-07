package com.stu.syllabuskt.personal

import android.os.Bundle
import android.view.View

/**
 *yuan
 *2020/9/7
 **/
interface IDispatcher {
    fun onCreate(bundle: Bundle?)

    fun onCreateView(view: View)

    fun onResume()

    fun onDestroy()
}