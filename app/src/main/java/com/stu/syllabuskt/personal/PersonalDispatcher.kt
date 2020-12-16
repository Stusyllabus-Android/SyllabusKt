package com.stu.syllabuskt.personal

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.view.OptionsPickerView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.main.MainActivity
import com.stu.syllabuskt.personal.theme.ThemePickerAdapter
import com.stu.syllabuskt.personal.theme.ThemePickerFragment
import com.stu.syllabuskt.personal.theme.ThemeUtil
import com.stu.syllabuskt.syllabus.SyllabusContainerFragment
import kotlin.collections.ArrayList

/**
 *yuan
 *2020/9/7
 **/
class PersonalDispatcher(val fragment: PersonalFragment): IDispatcher, View.OnClickListener {

    private val TAG = "PersonalDispatcher"
  
    lateinit var titleTV: TextView
  
    lateinit var changeSemesterLayout: RelativeLayout
    lateinit var changeThemeLayout: RelativeLayout
    lateinit var schoolSmartCardLayout: RelativeLayout
//    lateinit var examinationLayout: RelativeLayout
    lateinit var gradeLayout: RelativeLayout
    lateinit var settingLayout: RelativeLayout
    lateinit var currentAccountTV: TextView
    //设置学期
    private lateinit var mSemesterTextView: TextView
    private lateinit var years: ArrayList<String>
    private lateinit var semester: ArrayList<String>

    override fun onCreate(bundle: Bundle?) {

    }

    override fun onCreateView(view: View) {
        initView(view)
        initEvent()
    }

    private fun initView(view: View) {
        view.let {
            it.findViewById<TextView>(R.id.titleBarTV).apply { text = fragment.getText(R.string.personal)}
            currentAccountTV = (it.findViewById(R.id.currentAccountTV) as TextView).apply { text = StuContext.getDBService().getUserAccount(fragment.context) }
            changeSemesterLayout = it.findViewById(R.id.changeSemesterLayout)
            mSemesterTextView = (it.findViewById(R.id.semesterTextView) as  TextView).apply {
                val semesterSet = StuContext.getSharePreferences(fragment.context!!).getString(SyllabusContainerFragment.CurrentSemesterKey, "Non-existent")
                text = if (semesterSet == "Non-existent") "未设置当前学期" else semesterSet
            }
            changeThemeLayout = it.findViewById(R.id.changeThemeLayout)
            schoolSmartCardLayout = it.findViewById(R.id.smartCardLayout)
//            examinationLayout = it.findViewById(R.id.examinationLayout)
            gradeLayout = it.findViewById(R.id.gradeLayout)
            settingLayout = it.findViewById(R.id.settingLayout)
        }
//        view.findViewById<ImageView>(R.id.headIV).let {
//            Glide.with(fragment.context!!)
//                .load("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3343831728,3984740061&fm=26&gp=0.jpg")
//                .apply(RequestOptions.circleCropTransform())
//                .into(it)
//        }
    }

    private fun initEvent() {
        years = arrayListOf("2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021")
        semester = arrayListOf("春季学期", "秋季学期", "夏季学期")
        this.let {
            changeSemesterLayout.setOnClickListener(it)
            changeThemeLayout.setOnClickListener(it)
            schoolSmartCardLayout.setOnClickListener(it)
//            examinationLayout.setOnClickListener(it)
            gradeLayout.setOnClickListener(it)
            settingLayout.setOnClickListener(it)

        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.changeSemesterLayout -> showSemesterSelect()
            R.id.changeThemeLayout -> showThemePickView()
            R.id.smartCardLayout -> startCardView()
//            R.id.examinationLayout -> startExamView()
            R.id.gradeLayout -> startGradeView()
            R.id.settingLayout -> startSetting()
        }
    }

    private fun showSemesterSelect() {
        val none = ArrayList<String>()
        val optionsPickerView: OptionsPickerView<*> =
            OptionsPickerBuilder(fragment.context!!) { i, i1, i2, view ->
                mSemesterTextView.text = "${years[i]} ${semester[i2]}"
                Log.d(TAG, "onOptionsSelect: ${years[i]} ${semester[i2]}")
                StuContext.getSharePreferences(fragment.context!!).edit().putString(SyllabusContainerFragment.CurrentSemesterKey, "${years[i]} ${semester[i2]}").apply()
            }.setTitleText("选择学期")
                .setContentTextSize(18)
                .setDividerColor(Color.GRAY)
                .setSelectOptions(0, 0)
                .setBgColor(Color.WHITE)
                .isRestoreItem(true)
                .isCenterLabel(false)
                .build<Any>()
        optionsPickerView.setNPicker(
            years as List<Nothing>?, none as List<Nothing>?,
            semester as List<Nothing>?
        )
        optionsPickerView.show()
    }

    private fun showThemePickView() {
        ThemePickerFragment().apply {
            setOnItemClickListener(object : ThemePickerAdapter.OnItemClickListener {
                override fun onClick(name: String) {
                    ThemeUtil.setStyleName(name)
                    dismiss()
                    startActivity(Intent(fragment.context, MainActivity::class.java))
                    fragment.activity?.finish()
                    fragment.activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            })
            show(fragment.childFragmentManager, "ThemePickerFragment")
        }
    }

    override fun onResume() {

    }

    override fun onDestroy() {

    }
}