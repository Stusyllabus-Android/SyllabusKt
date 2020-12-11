package com.stu.syllabuskt.syllabus

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import com.balysv.materialripple.MaterialRippleLayout
import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseFragment
import com.stu.syllabuskt.bean.Lesson
import com.stu.syllabuskt.utils.ColorUtil
import com.stu.syllabuskt.utils.ToastUtil

private const val POSITION_PARAM = "position_param"

class SyllabusFragment : BaseFragment(), ISyllabusContract.IView {

    private val TAG = "SyllabusFragment"

    private var selectedWeek: Int? = null
    private var weekIndex: Int = 0
    private lateinit var syllabusPresenter: SyllabusPresenter
    private lateinit var mDateLinearLayout: LinearLayout
    private lateinit var mTimeLinearLayout: LinearLayout
    private lateinit var gridLayout: GridLayout
    private var detailTimeWidth: Int = 0
    private var gridWidth: Int = 0
    private var gridHeight: Int = 0
    private var timeWidth: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedWeek = it.getInt(POSITION_PARAM)
            weekIndex = selectedWeek!! - 1
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.windowManager?.defaultDisplay?.let {
            gridWidth = it.width * 2 / 15
            gridHeight = it.height / 12
            timeWidth = it.width - gridWidth * 7
        }
        syllabusPresenter = SyllabusPresenter(context!!, this)
        return inflater.inflate(R.layout.fragment_syllabus, container, false).apply {
            mDateLinearLayout = findViewById(R.id.dateLinearLayout)
            mTimeLinearLayout = findViewById(R.id.timeLinearLayout)
            gridLayout = findViewById<GridLayout>(R.id.gridLayout).apply {
                columnCount = 7
                rowCount = 13
            }
            showDate()
            showTime()
            initGridLayout()
            syllabusPresenter.init()
        }
    }

    private fun showDate() {
        run {
            val blankTextView = activity!!.layoutInflater.inflate(R.layout.week_grid, null, false) as TextView
            val layoutParams = LinearLayout.LayoutParams(timeWidth, ViewGroup.LayoutParams.MATCH_PARENT)
            mDateLinearLayout.addView(blankTextView, layoutParams)
        }
        for (i in 0..6) {
            val weekString = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
            val weekTextView = activity!!.layoutInflater.inflate(R.layout.week_grid, null, false) as TextView
            weekTextView.text = weekString[i]
            if (i + 1 == 7) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    weekTextView.background = resources.getDrawable(R.drawable.bg_grid_week_end)
                } else {
                    weekTextView.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_grid_week_end))
                }
            }
            val layoutParams = LinearLayout.LayoutParams(gridWidth, ViewGroup.LayoutParams.MATCH_PARENT)
            mDateLinearLayout.addView(weekTextView, layoutParams)
        }
    }

    private fun showTime() {
        for (i in 1..13) {
            val timeTextView = LayoutInflater.from(activity).inflate(R.layout.week_grid, null, false) as TextView
            timeTextView.text = time2char(i).toString()
            if (i == 13) {
                timeTextView.background = resources.getDrawable(R.drawable.bg_grid_time_end)
            }
            val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(timeWidth, gridHeight)
            mTimeLinearLayout.addView(timeTextView, layoutParams)
        }
    }

    private fun time2char(time: Int): Char {
        return when {
            time < 10 -> {
                (time + '0'.toInt()).toChar()
            }
            time > 10 -> {
                ('A'.toInt() + (time - 11)).toChar()
            }
            else -> {
                '0'
            }
        }
    }

    private fun initGridLayout() {
        gridLayout.removeAllViews()
        for (i in 0..6) {
            for (j in 0..12) {
                val lessonLinearLayout = LayoutInflater.from(activity)
                    .inflate(R.layout.lesson_grid, null, false) as LinearLayout
                val lessonRippleLayout: MaterialRippleLayout =
                    lessonLinearLayout.findViewById<View>(R.id.lessonInfoRipple) as MaterialRippleLayout
                val lessonTextView =
                    lessonLinearLayout.findViewById<View>(R.id.lessonTextView) as TextView
                lessonLinearLayout.visibility = View.INVISIBLE
                lessonRippleLayout.isEnabled = false
                lessonTextView.width = gridWidth
                lessonTextView.height = gridHeight
                val rowSpec = GridLayout.spec(j, 1)
                val columnSpec = GridLayout.spec(i)
                gridLayout.addView(lessonLinearLayout, GridLayout.LayoutParams(rowSpec, columnSpec))
            }
        }
    }

    override fun showMSG(msg: String) {
        ToastUtil.showShort(context, msg)
    }

    override fun showSyllabus(lessonBeanList: List<Lesson>) {
        Log.d(TAG, "showSyllabus: " + lessonBeanList.size)

        // 遍历转换格式后的课程并添加至格子
        var i = 0
        for (showLessonBean in lessonBeanList) {
            showLessonBean.bgColor = ColorUtil.bgColors.get(i++ % ColorUtil.bgColors.size)
            val week: List<String> = showLessonBean.getDuration().split("-")
            val startWeek = week[0].toInt()
            val endWeek = week[1].toInt()
            if (startWeek > weekIndex || endWeek < weekIndex) continue

            // TODO: 2019/12/5 写了大量重复的代码
            if (showLessonBean.getDays().getW0() != null && showLessonBean.getDays().getW0() != "-") {
                Log.d(TAG, "showSyllabus: 周日" + showLessonBean.getDays().getW0())
            }
            if (showLessonBean.getDays().getW1() != null && showLessonBean.getDays().getW1() != "-") {
                Log.d(TAG, "showSyllabus: 周一" + showLessonBean.getDays().getW1())
                val time: List<String> = showLessonBean.getDays().getW1().split("-")
                val lessonLinearLayout = LayoutInflater.from(activity)
                    .inflate(R.layout.lesson_grid, null, false) as LinearLayout
                val lessonRippleLayout: MaterialRippleLayout =
                    lessonLinearLayout.findViewById<View>(R.id.lessonInfoRipple) as MaterialRippleLayout
                lessonRippleLayout.setOnClickListener(View.OnClickListener {
                    val days: String =
                        showLessonBean.getDuration().toString() + "周\n" + showLessonBean.getDays()
                            .toString()
//                    mActivity.startActivity(
//                        LessonInfoActivity.getIntent(
//                            mActivity,
//                            showLessonBean.getBgColor(),
//                            showLessonBean.getName(),
//                            showLessonBean.getId(),
//                            showLessonBean.getCredit(),
//                            showLessonBean.getRoom(),
//                            showLessonBean.getTeacher(),
//                            days
//                        )
//                    )
                })
                val lessonTextView =
                    lessonLinearLayout.findViewById<View>(R.id.lessonTextView) as TextView
                lessonTextView.text = showLessonBean.getName().toString() + "\n@" + showLessonBean.getRoom()
                lessonTextView.width = gridWidth
                lessonTextView.height = gridHeight * (time[1].toInt() - time[0].toInt() + 1)
                val shape = resources.getDrawable(R.drawable.grid_background) as GradientDrawable
                shape.setColor(
                    ColorUtils.setAlphaComponent(
                        resources.getColor(
                            showLessonBean.getBgColor()
                        ), 192
                    )
                )
                lessonTextView.setBackgroundDrawable(shape)
                val rowSpec =
                    GridLayout.spec(time[0].toInt() - 1, time[1].toInt() - time[0].toInt() + 1)
                val columnSpec = GridLayout.spec(1)
                gridLayout.addView(lessonLinearLayout, GridLayout.LayoutParams(rowSpec, columnSpec))
            }
            if (showLessonBean.getDays().getW2() != null && showLessonBean.getDays().getW2() != "-") {
                Log.d(TAG, "showSyllabus: 周二" + showLessonBean.getDays().getW2())
                val time: List<String> = showLessonBean.getDays().getW2().split("-")
                val lessonLinearLayout = LayoutInflater.from(activity)
                    .inflate(R.layout.lesson_grid, null, false) as LinearLayout
                val lessonRippleLayout: MaterialRippleLayout = lessonLinearLayout.findViewById<View>(R.id.lessonInfoRipple) as MaterialRippleLayout
                lessonRippleLayout.setOnClickListener(View.OnClickListener {
                    val days: String =
                        showLessonBean.getDuration().toString() + "周\n" + showLessonBean.getDays()
                            .toString()
//                    mActivity.startActivity(
//                        LessonInfoActivity.getIntent(
//                            mActivity,
//                            showLessonBean.getBgColor(),
//                            showLessonBean.getName(),
//                            showLessonBean.getId(),
//                            showLessonBean.getCredit(),
//                            showLessonBean.getRoom(),
//                            showLessonBean.getTeacher(),
//                            days
//                        )
//                    )
                })
                val lessonTextView = lessonLinearLayout.findViewById<View>(R.id.lessonTextView) as TextView
                lessonTextView.text = showLessonBean.getName().toString() + "\n@" + showLessonBean.getRoom()
                Log.d(TAG, "showSyllabus: " + showLessonBean.getRoom())
                lessonTextView.width = gridWidth
                lessonTextView.height = gridHeight * (time[1].toInt() - time[0].toInt() + 1)
                val shape = resources.getDrawable(R.drawable.grid_background) as GradientDrawable
                shape.setColor(ColorUtils.setAlphaComponent(resources.getColor(showLessonBean.getBgColor()), 192))
                lessonTextView.setBackgroundDrawable(shape)
                val rowSpec = GridLayout.spec(time[0].toInt() - 1, time[1].toInt() - time[0].toInt() + 1)
                val columnSpec = GridLayout.spec(2)
                gridLayout.addView(lessonLinearLayout, GridLayout.LayoutParams(rowSpec, columnSpec))
            }
            if (showLessonBean.getDays().getW3() != null && showLessonBean.getDays().getW3() != "-") {
                Log.d(TAG, "showSyllabus: 周三" + showLessonBean.getDays().getW3())
                val time: List<String> = showLessonBean.getDays().getW3().split("-")
                val lessonLinearLayout = LayoutInflater.from(activity)
                    .inflate(R.layout.lesson_grid, null, false) as LinearLayout
                val lessonRippleLayout: MaterialRippleLayout = lessonLinearLayout.findViewById<View>(R.id.lessonInfoRipple) as MaterialRippleLayout
                lessonRippleLayout.setOnClickListener(View.OnClickListener {
                    val days: String =
                        showLessonBean.getDuration().toString() + "周\n" + showLessonBean.getDays()
                            .toString()
//                    mActivity.startActivity(
//                        LessonInfoActivity.getIntent(
//                            mActivity,
//                            showLessonBean.getBgColor(),
//                            showLessonBean.getName(),
//                            showLessonBean.getId(),
//                            showLessonBean.getCredit(),
//                            showLessonBean.getRoom(),
//                            showLessonBean.getTeacher(),
//                            days
//                        )
//                    )
                })
                val lessonTextView = lessonLinearLayout.findViewById<View>(R.id.lessonTextView) as TextView
                lessonTextView.text = showLessonBean.getName().toString() + "\n@" + showLessonBean.getRoom()
                lessonTextView.width = gridWidth
                lessonTextView.height = gridHeight * (time[1].toInt() - time[0].toInt() + 1)
                val shape = resources.getDrawable(R.drawable.grid_background) as GradientDrawable
                shape.setColor(ColorUtils.setAlphaComponent(resources.getColor(showLessonBean.bgColor), 192))
                lessonTextView.setBackgroundDrawable(shape)
                val rowSpec = GridLayout.spec(time[0].toInt() - 1, time[1].toInt() - time[0].toInt() + 1)
                val columnSpec = GridLayout.spec(3)
                gridLayout.addView(lessonLinearLayout, GridLayout.LayoutParams(rowSpec, columnSpec))
            }
            if (showLessonBean.getDays().getW4() != null && showLessonBean.getDays().getW4() != "-") {
                Log.d(TAG, "showSyllabus: 周四" + showLessonBean.getDays().getW4())
                val time: List<String> = showLessonBean.getDays().getW4().split("-")
                val lessonLinearLayout = LayoutInflater.from(activity)
                    .inflate(R.layout.lesson_grid, null, false) as LinearLayout
                val lessonRippleLayout: MaterialRippleLayout = lessonLinearLayout.findViewById<View>(R.id.lessonInfoRipple) as MaterialRippleLayout
                lessonRippleLayout.setOnClickListener(View.OnClickListener {
                    val days: String = showLessonBean.getDuration().toString() + "周\n" + showLessonBean.getDays().toString()
//                    mActivity.startActivity(
//                        LessonInfoActivity.getIntent(
//                            mActivity,
//                            showLessonBean.getBgColor(),
//                            showLessonBean.getName(),
//                            showLessonBean.getId(),
//                            showLessonBean.getCredit(),
//                            showLessonBean.getRoom(),
//                            showLessonBean.getTeacher(),
//                            days
//                        )
//                    )
                })
                val lessonTextView = lessonLinearLayout.findViewById<View>(R.id.lessonTextView) as TextView
                lessonTextView.text = showLessonBean.getName().toString() + "\n@" + showLessonBean.getRoom()
                lessonTextView.width = gridWidth
                lessonTextView.height = gridHeight * (time[1].toInt() - time[0].toInt() + 1)
                val shape = resources.getDrawable(R.drawable.grid_background) as GradientDrawable
                shape.setColor(
                    ColorUtils.setAlphaComponent(
                        resources.getColor(
                            showLessonBean.getBgColor()
                        ), 192
                    )
                )
                lessonTextView.setBackgroundDrawable(shape)
                val rowSpec =
                    GridLayout.spec(time[0].toInt() - 1, time[1].toInt() - time[0].toInt() + 1)
                val columnSpec = GridLayout.spec(4)
                gridLayout.addView(lessonLinearLayout, GridLayout.LayoutParams(rowSpec, columnSpec))
            }
            if (showLessonBean.getDays().getW5() != null && showLessonBean.getDays().getW5() != "-") {
                Log.d(TAG, "showSyllabus: 周五" + showLessonBean.getDays().getW5())
                val time: List<String> = showLessonBean.getDays().getW5().split("-")
                val lessonLinearLayout = LayoutInflater.from(activity)
                    .inflate(R.layout.lesson_grid, null, false) as LinearLayout
                val lessonRippleLayout: MaterialRippleLayout = lessonLinearLayout.findViewById<View>(R.id.lessonInfoRipple) as MaterialRippleLayout
                lessonRippleLayout.setOnClickListener(View.OnClickListener {
                    val days: String =
                        showLessonBean.getDuration().toString() + "周\n" + showLessonBean.getDays()
                            .toString()
//                    mActivity.startActivity(
//                        LessonInfoActivity.getIntent(
//                            mActivity,
//                            showLessonBean.getBgColor(),
//                            showLessonBean.getName(),
//                            showLessonBean.getId(),
//                            showLessonBean.getCredit(),
//                            showLessonBean.getRoom(),
//                            showLessonBean.getTeacher(),
//                            days
//                        )
//                    )
                })
                val lessonTextView =
                    lessonLinearLayout.findViewById<View>(R.id.lessonTextView) as TextView
                lessonTextView.text = showLessonBean.getName().toString() + "\n@" + showLessonBean.getRoom()
                lessonTextView.width = gridWidth
                lessonTextView.height = gridHeight * (time[1].toInt() - time[0].toInt() + 1)
                val shape = resources.getDrawable(R.drawable.grid_background) as GradientDrawable
                shape.setColor(ColorUtils.setAlphaComponent(resources.getColor(showLessonBean.getBgColor()), 192))
                lessonTextView.setBackgroundDrawable(shape)
                val rowSpec = GridLayout.spec(time[0].toInt() - 1, time[1].toInt() - time[0].toInt() + 1)
                val columnSpec = GridLayout.spec(5)
                gridLayout.addView(lessonLinearLayout, GridLayout.LayoutParams(rowSpec, columnSpec))
            }
            if (showLessonBean.getDays().getW6() != null && showLessonBean.getDays().getW6() != "-") {
                Log.d(TAG, "showSyllabus: 周六" + showLessonBean.getDays().getW6())
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(selectedWeek: Int) =
            SyllabusFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION_PARAM, selectedWeek)
                }
            }
    }
}