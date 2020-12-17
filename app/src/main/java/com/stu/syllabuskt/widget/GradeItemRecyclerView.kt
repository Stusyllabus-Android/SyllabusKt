package com.stu.syllabuskt.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by yuan on 2020/12/17
 */
class GradeItemRecyclerView(context: Context, attributeSet: AttributeSet) :RecyclerView(context, attributeSet) {

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return false
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        return false
    }
}