package com.stu.syllabuskt.personal.setting

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.stu.syllabuskt.App
import com.stu.syllabuskt.R
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.api.OwnApi
import com.stu.syllabuskt.api.RetrofitProvider
import com.stu.syllabuskt.base.BaseActivity
import com.stu.syllabuskt.utils.ToastUtil
import retrofit2.Call
import retrofit2.Response

class FeedbackActivity : BaseActivity() {

    private lateinit var feedbackET: EditText
    private lateinit var submitBtn: Button

    private val ownApi = RetrofitProvider.getOwnRetrofit().create(OwnApi::class.java)

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
        if (content.length < 7) {
            ToastUtil.showShort(this, "请输入不少于7个字符")
            return
        }
        ownApi.postFeedback(
            StuContext.getDBService().getUserAccount(this),
            content,
            App.versionCode,
            "手机厂商： ${android.os.Build.BRAND}, 手机型号： ${android.os.Build.MODEL}, 系统版本号：${android.os.Build.VERSION.RELEASE}"
        ).enqueue(object : retrofit2.Callback<FeedbackCallBack> {
            override fun onResponse(
                call: Call<FeedbackCallBack>,
                response: Response<FeedbackCallBack>
            ) {
                if (response.body()?.code == "1") {
                    feedbackET.setText("")
                    ToastUtil.showShort(this@FeedbackActivity, "已收到您的反馈~")
                } else {
                    ToastUtil.showShort(this@FeedbackActivity, "反馈发送失败")
                }
            }

            override fun onFailure(call: Call<FeedbackCallBack>, t: Throwable) {
                ToastUtil.showShort(this@FeedbackActivity, "反馈发送失败")
            }
        })
    }

}

data class FeedbackCallBack(val msg: String, val code: String)