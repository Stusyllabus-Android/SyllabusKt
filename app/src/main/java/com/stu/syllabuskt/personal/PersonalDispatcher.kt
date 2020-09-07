package com.stu.syllabuskt.personal

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.stu.syllabuskt.R
import kotlinx.android.synthetic.main.fragment_personal.view.*

/**
 *yuan
 *2020/9/7
 **/
class PersonalDispatcher(val fragment: PersonalFragment): IDispatcher, View.OnClickListener {

    lateinit var changeSemesterLayout: RelativeLayout
    lateinit var changeThemeLayout: RelativeLayout
    lateinit var schoolSmartCardLayout: RelativeLayout
    lateinit var examinationLayout: RelativeLayout
    lateinit var gradeLayout: RelativeLayout
    lateinit var settingLayout: RelativeLayout

    override fun onCreate(bundle: Bundle?) {

    }

    override fun onCreateView(view: View) {
        initView(view)
        initEvent()
    }

    private fun initView(view: View) {
        view.let {
            changeSemesterLayout = it.findViewById(R.id.changeSemesterLayout)
            changeThemeLayout = it.findViewById(R.id.changeThemeLayout)
            schoolSmartCardLayout = it.findViewById(R.id.smartCardLayout)
            examinationLayout = it.findViewById(R.id.examinationLayout)
            gradeLayout = it.findViewById(R.id.gradeLayout)
            settingLayout = it.findViewById(R.id.settingLayout)

        }
    }

    private fun initEvent() {
        this.let {
            changeSemesterLayout.setOnClickListener(it)
            changeThemeLayout.setOnClickListener(it)
            schoolSmartCardLayout.setOnClickListener(it)
            examinationLayout.setOnClickListener(it)
            gradeLayout.setOnClickListener(it)
            settingLayout.setOnClickListener(it)

        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.smartCardLayout -> startCardView()
            R.id.examinationLayout -> startExamView()
            R.id.gradeLayout -> startGradeView()
            R.id.settingLayout -> startSetting()
        }
    }

    override fun onResume() {

    }

    override fun onDestroy() {

    }
}