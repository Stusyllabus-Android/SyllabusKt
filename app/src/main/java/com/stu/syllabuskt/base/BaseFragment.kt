package com.stu.syllabuskt.base

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Create by yuan on 2020/12/5
 */
abstract class BaseFragment: Fragment() {

    private val mHandler = Handler()

    fun runOnUiThread(action: () -> Unit) {
        mHandler.post(action)
    }
}