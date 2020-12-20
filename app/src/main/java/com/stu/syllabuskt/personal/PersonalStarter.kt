package com.stu.syllabuskt.personal

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.stu.syllabuskt.login.LoginActivity
import com.stu.syllabuskt.personal.card.CardActivity
import com.stu.syllabuskt.personal.exam.ExaminationActivity
import com.stu.syllabuskt.personal.grade.GradeActivity
import com.stu.syllabuskt.personal.mail.MailActivity
import com.stu.syllabuskt.personal.setting.SettingActivity

/**
 *yuan
 *2020/9/7
 **/

fun PersonalDispatcher.startCardView() {
    fragment.startActivity(Intent(fragment.context, CardActivity::class.java))
}

fun PersonalDispatcher.startExamView() {
    fragment.startActivity(Intent(fragment.context, ExaminationActivity::class.java),)
}

fun PersonalDispatcher.startGradeView() {
    fragment.startActivity(Intent(fragment.context, GradeActivity::class.java))
}

fun PersonalDispatcher.startMailView() {
    fragment.startActivity(Intent(fragment.context, MailActivity::class.java))
}

fun PersonalDispatcher.startSetting() {
    fragment.startActivityForResult(Intent(fragment.context, SettingActivity::class.java), 1000)
}

fun PersonalFragment.toLoginView() {
    startActivity(Intent(context, LoginActivity::class.java))
    activity?.finish()
}