package com.stu.syllabuskt.personal.setting

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseActivity

class FeedbackActivity : BaseActivity() {

    private lateinit var feedbackET: EditText
    private lateinit var submitBtn: Button

    override fun getContentView(): Int {
        return R.layout.activity_feedback
    }

    override fun init() {
        super.init()
        findViewById<ImageView>(R.id.backIcon).apply {
            visibility = View.VISIBLE
            setOnClickListener { finish() }
        }
        findViewById<TextView>(R.id.titleBarTV).text = "帮助与反馈"
        feedbackET = findViewById(R.id.feedbackEditText)
        submitBtn = findViewById(R.id.submitBtn)
        submitBtn.setOnClickListener {
            doFeedBack(feedbackET.text.toString())
        }
    }

    private fun doFeedBack(content: String) {

    }

}