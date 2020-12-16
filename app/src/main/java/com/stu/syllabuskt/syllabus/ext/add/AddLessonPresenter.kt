package com.stu.syllabuskt.syllabus.ext.add

import android.content.Context
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.syllabus.SyllabusContainerFragment
import com.stu.syllabuskt.utils.ToastUtil

/**
 * Create by yuan on 2020/12/14
 */
class AddLessonPresenter(val context: Context, val view: AddLessonContract.IView) : AddLessonContract.IPresenter {

    private val model = AddLessonModel(context)

    override fun addLesson(
        lessonName: String,
        classroom: String,
        weekSelected: String,
        detail: String
    ) {
        if (StuContext.getSharePreferences(context).getString(SyllabusContainerFragment.CurrentSemesterKey, "Non-existent") == "Non-existent") {
            view.showErrorMSG("请先到个人主页设置当前学年学期~")
        } else if (lessonName.isEmpty()||classroom.isEmpty()||weekSelected.isEmpty()||detail.isEmpty()){
            view.showErrorMSG("信息未填写完整")
        }else {
            var splitString=weekSelected.split("-")
            if (Integer.parseInt(splitString[1])-Integer.parseInt(splitString[0])<0){
                view.showErrorMSG("开始周数不能大于结束周数")
                return
            }
            splitString=detail.split(" ")[1].split("-")
            if (Integer.parseInt(splitString[1])-Integer.parseInt(splitString[0])<0){
                view.showErrorMSG("开始时间不能大于结束周数")
                return
            }

            model.addLesson(lessonName, classroom, weekSelected, detail)
            view.showSuccessMessage("添加成功")
        }
    }
}