package com.stu.syllabuskt.personal.setting

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.base.BaseActivity
import com.stu.syllabuskt.CheckUpdateModel
import com.stu.syllabuskt.Result
import com.stu.syllabuskt.syllabus.SyllabusContainerFragment
import com.stu.syllabuskt.utils.ToastUtil

class AboutUsActivity : BaseActivity() {

    override fun getContentView(): Int {
        return R.layout.activity_about_us
    }

    override fun init() {
        super.init()
        findViewById<TextView>(R.id.titleBarTV).apply { text = "关于我们" }
        findViewById<ImageView>(R.id.backIcon).apply {
            visibility = View.VISIBLE
            setOnClickListener { finish() }
        }
        findViewById<Button>(R.id.checkUpdateBtn).setOnClickListener {
            CheckUpdateModel().canUpdateWithVersionCode(object : CheckUpdateModel.UpdateListener {
                override fun canUpdate(result: Result) {
                    if (result.canUpdate && StuContext.getSharePreferences(this@AboutUsActivity).getString(
                            SyllabusContainerFragment.CurrentSemesterKey,"Non-existent") != "Non-existent") {
                        AlertDialog.Builder(this@AboutUsActivity)
                            .setTitle("版本更新提醒")
                            .setMessage(result.versionInfo?.description ?: "")
                            .setPositiveButton("更新"
                            ) { dialog, which ->
                                val uri: Uri = Uri.parse("http://d.firim.top/stupie")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                startActivity(intent)
                            }
                            .create().show()
                    } else ToastUtil.showShort(this@AboutUsActivity, "当前版本已是最新版")
                }
            })
        }
    }
}