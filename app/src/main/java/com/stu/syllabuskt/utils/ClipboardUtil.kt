package com.stu.syllabuskt.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.stu.syllabuskt.App

/**
 * Create by yuan on 2020/12/18
 */
object ClipboardUtil {
    fun copyToClipboard(text: String) {
        val clipboardManager = App.getContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clipData)
    }
}