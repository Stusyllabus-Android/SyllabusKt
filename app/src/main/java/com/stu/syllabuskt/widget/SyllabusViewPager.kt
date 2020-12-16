package com.stu.syllabuskt.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Create by yuan on 2020/12/16
 */
class CtlScrollViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var scrollable = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (!scrollable) return true
        return super.onTouchEvent(ev)
    }

    override fun scrollTo(x: Int, y: Int) {
        if (scrollable) {
            super.scrollTo(x, y)
        }
    }

    public fun isScrollable(): Boolean = scrollable

    public fun setScrollable(scrollable: Boolean) {
        this.scrollable = scrollable
    }
}