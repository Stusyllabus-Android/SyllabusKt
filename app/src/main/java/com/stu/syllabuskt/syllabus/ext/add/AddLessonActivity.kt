package com.stu.syllabuskt.syllabus.ext.add

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.view.OptionsPickerView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseActivity
import com.stu.syllabuskt.syllabus.SyllabusContainerFragment
import com.stu.syllabuskt.utils.ToastUtil

class AddLessonActivity : BaseActivity(), AddLessonContract.IView, View.OnClickListener {

    private val TAG = "AddLessonActivity"

    lateinit var presenter: AddLessonContract.IPresenter
    lateinit var titleTextView: TextView
    lateinit var backIcon: ImageView
    lateinit var lessonNameET: EditText
    lateinit var classroomET: EditText
    lateinit var customWeekTV: TextView
    lateinit var detailTV: TextView
    lateinit var submitBtn: Button

    private var weekSelected: String? = null
    private var timeSelected: String? = null

    override fun getContentView(): Int {
        return R.layout.activity_add_lesson
    }

    override fun init() {
        super.init()
        presenter = AddLessonPresenter(this, this)
        titleTextView = findViewById<TextView>(R.id.titleBarTV).apply { text = "添加课程" }
        backIcon = findViewById<ImageView>(R.id.backIcon).apply {
            visibility = View.VISIBLE
        }
        lessonNameET = findViewById(R.id.lessonNameEditText)
        classroomET = findViewById(R.id.classroomEditText)
        customWeekTV = findViewById(R.id.customWeek)
        detailTV = findViewById(R.id.detail)
        submitBtn = findViewById(R.id.addLessonButton)
        initEvent()
    }

    private fun initEvent() {
        backIcon.setOnClickListener(this)
        customWeekTV.setOnClickListener(this)
        detailTV.setOnClickListener(this)
        submitBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.backIcon -> finish()
            R.id.customWeek -> chooseWeek()
            R.id.detail -> chooseDetail()
            R.id.addLessonButton -> presenter.addLesson(lessonNameET.text.toString(), classroomET.text.toString(), weekSelected ?: "", timeSelected ?: "")
        }
    }

    override fun chooseWeek(): String {
        val startWeek = mutableListOf<Int>()
        val endWeek = mutableListOf<List<Int>>()
        for (i in 1..20) {
            startWeek.add(i)
        }
        for (i in 1..20) {
            endWeek.add(startWeek)
        }
        val optionsPickerView: OptionsPickerView<*> = OptionsPickerBuilder(
            this
        ) { i, i1, i2, view ->
            weekSelected = startWeek.get(i).toString() + "-" + endWeek.get(i).get(i1)
            customWeekTV.text = weekSelected
        }.setTitleText("周数选择")
            .setContentTextSize(18)
            .setDividerColor(Color.GRAY)
            .setSelectOptions(0, 0)
            .setBgColor(Color.WHITE)
            .isRestoreItem(true)
            .isCenterLabel(false)
            .build<Any>()
        optionsPickerView.setPicker(startWeek as List<Nothing>?, endWeek as List<Nothing>?)
        optionsPickerView.show()
        Log.d(TAG, "chooseWeek: $weekSelected")
        return weekSelected ?: ""
    }

    override fun chooseDetail() {
        val day = mutableListOf<String>()
        val time = mutableListOf<String>()
        for (i in 1..7) {
            when(i) {
                1 -> "周日"
                2 -> "周一"
                3 -> "周二"
                4 -> "周三"
                5 -> "周四"
                6 -> "周五"
                7 -> "周六"
                else -> ""
            }.let {
                day.add(it)
            }
        }
        for (i in 1..13) {
            when {
                i < 10 -> time.add(i.toString())
                i == 10 -> time.add("0")
                i == 11 -> time.add("A")
                i == 12 -> time.add("B")
                else -> time.add("C")
            }
        }
        val optionsPickerView: OptionsPickerView<*> = OptionsPickerBuilder(
            this
        ) { i, i1, i2, view ->
            timeSelected = day.get(i).toString() + " " + time.get(i1) + "-" + time.get(i2)
            detailTV.text = timeSelected
        }.setTitleText("时间选择")
            .setContentTextSize(18)
            .setDividerColor(Color.GRAY)
            .setSelectOptions(0, 0)
            .setBgColor(Color.WHITE)
            .isRestoreItem(true)
            .isCenterLabel(false)
            .build<Any>()
        optionsPickerView.setNPicker(day as List<Nothing>?, time as List<Nothing>?, time)
        optionsPickerView.show()
    }

    override fun showErrorMSG(msg: String) {
        ToastUtil.showShort(this, msg)
    }

    override fun showSuccessMessage(msg: String) {
        ToastUtil.showShort(this, msg)
        setResult(SyllabusContainerFragment.ADD_LESSON_CODE, Intent().putExtra("hadAddNewLesson", "hadAddNewLesson"))
    }


}