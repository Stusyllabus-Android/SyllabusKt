package com.stu.syllabuskt.widget

import android.content.Context

/**
 * Create by yuan on 2020/12/15
 */
object LayoutUtil {

    fun getScreenWidth(context: Context): Int {
        return px2dp(context, context.resources.displayMetrics.widthPixels.toFloat())
    }

    fun getScreenHeight(context: Context): Int {
        return px2dp(context, context.resources.displayMetrics.heightPixels.toFloat())
    }

    fun dp2px(context: Context, dp: Float): Int {
        return (dp * context.resources.displayMetrics.density + 0.5f).toInt()
    }

    fun px2dp(context: Context, px: Float): Int {
        return (px / context.resources.displayMetrics.density + 0.5f).toInt()
    }
}