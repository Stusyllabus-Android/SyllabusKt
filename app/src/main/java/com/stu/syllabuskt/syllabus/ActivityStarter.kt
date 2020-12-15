package com.stu.syllabuskt.syllabus

import android.content.Intent
import com.stu.syllabuskt.base.BaseFragment
import com.stu.syllabuskt.syllabus.ext.add.AddLessonActivity
import com.stu.syllabuskt.syllabus.ext.delete.DeleteLessonActivity

/**
 * Create by yuan on 2020/12/14
 */

fun BaseFragment.startAddLesson() {
    startActivityForResult(Intent(this.context, AddLessonActivity::class.java), SyllabusContainerFragment.ADD_LESSON_CODE)
}

fun BaseFragment.startDeleteLesson() {
    startActivity(Intent(this.context, DeleteLessonActivity::class.java))
}