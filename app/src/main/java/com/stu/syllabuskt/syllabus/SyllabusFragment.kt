package com.stu.syllabuskt.syllabus

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.balysv.materialripple.MaterialRippleLayout
import com.stu.syllabuskt.R
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.base.BaseFragment
import com.stu.syllabuskt.bean.Lesson
import com.stu.syllabuskt.login.Target
import com.stu.syllabuskt.login.YBBusinessModel
import com.stu.syllabuskt.utils.ColorUtil
import com.stu.syllabuskt.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_setting.view.*
import kotlinx.android.synthetic.main.fragment_syllabus.*

private const val POSITION_PARAM = "position_param"

class SyllabusFragment : BaseFragment(), ISyllabusContract.IView {

    private val TAG = "SyllabusFragment"

    private var selectedWeek: Int? = null
    private var weekIndex: Int = 0
    private lateinit var syllabusPresenter: SyllabusPresenter
    private lateinit var mRefreshLayout: SwipeRefreshLayout
    private lateinit var mDateLinearLayout: LinearLayout
    private lateinit var mTimeLinearLayout: LinearLayout
    private lateinit var gridLayout: GridLayout
    private var detailTimeWidth: Int = 0
    private var gridWidth: Int = 0
    private var gridHeight: Int = 0
    private var timeWidth: Int = 0

    private lateinit var ybBusinessModel: YBBusinessModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedWeek = it.getInt(POSITION_PARAM)
            weekIndex = selectedWeek!! + 1
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
        syllabusPresenter = SyllabusPresenter(context!!, this, object : SyllabusPresenter.IOListener {
            override fun onFinish(lessonList: List<Lesson>) {
                runOnUiThread {
                    showSyllabus(lessonList)
                }
            }
        })
        ybBusinessModel = YBBusinessModel(context!!, Target.Syllabus)
        return inflater.inflate(R.layout.fragment_syllabus, container, false).apply {
            mRefreshLayout = findViewById(R.id.refreshSyllabusLayout)
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
            initEvent()
        }
    }

    private fun initEvent() {
        mRefreshLayout.setOnRefreshListener {
            if (StuContext.getSharePreferences(context!!).getString(SyllabusContainerFragment.CurrentSemesterKey, "Non-existent") == "Non-existent") {
               ToastUtil.showShort(context!!, "请先到个人主页设置当前学年学期~")
                refreshSyllabusLayout.isRefreshing = false
                return@setOnRefreshListener
            }
            ybBusinessModel.login(
                StuContext.getDBService().getUserAccount(context!!),
                StuContext.getDBService().getUserPassword(context!!),
                object : YBBusinessModel.YBBusinessListener {
                    override fun onProgress() {
                        refreshSyllabusLayout.isRefreshing = true
                    }

                    override fun onSuccess() {
                        refreshSyllabusLayout.isRefreshing = false
                        gridLayout.removeAllViews()
                        initGridLayout()
                        syllabusPresenter.init()
                        ToastUtil.showShort(context!!, "刷新成功")
                    }

                    override fun onFailure(msg: String) {
                        refreshSyllabusLayout.isRefreshing = false
                        ToastUtil.showShort(context!!, msg)
                    }
                })
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
                    .inflate(R.layout.lesson_grid, null, false) as MaterialRippleLayout
                val lessonRippleLayout: MaterialRippleLayout = lessonLinearLayout.findViewById<View>(R.id.lessonInfoRipple) as MaterialRippleLayout
                val lessonTextView = lessonLinearLayout.findViewById<View>(R.id.lessonTextView) as TextView
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
        if (weekIndex == 1) {
            ToastUtil.showLong(context, msg)
        }
    }

    override fun showSyllabus(lessonList: List<Lesson>) {
        Log.d(TAG, "showSyllabus: lessonBeanList.size is ${lessonList.size} week index is $weekIndex")

        // 遍历转换格式后的课程并添加至格子
        var i = 0
        for (showLessonBean in lessonList) {
            showLessonBean.bgColor = ColorUtil.bgColors.get(i++ % ColorUtil.bgColors.size)
            val week: List<String> = showLessonBean.getDuration().split("-")
            val startWeek = week[0].toInt()
            val endWeek = week[1].toInt()
            if (startWeek > weekIndex || endWeek < weekIndex) continue

            if (showLessonBean.getDays().getW0() != null && showLessonBean.getDays().getW0() != "-") {
                Log.d(TAG, "showSyllabus: 周日" + showLessonBean.getDays().getW0())
                drawGrid(showLessonBean, showLessonBean.getDays().getW0(), 0)
            }
            if (showLessonBean.getDays().getW1() != null && showLessonBean.getDays().getW1() != "-") {
                Log.d(TAG, "showSyllabus: 周一" + showLessonBean.getDays().getW1())
                drawGrid(showLessonBean, showLessonBean.getDays().getW1(), 1)
            }
            if (showLessonBean.getDays().getW2() != null && showLessonBean.getDays().getW2() != "-") {
                Log.d(TAG, "showSyllabus: 周二" + showLessonBean.getDays().getW2())
                drawGrid(showLessonBean, showLessonBean.getDays().getW2(), 2)
            }
            if (showLessonBean.getDays().getW3() != null && showLessonBean.getDays().getW3() != "-") {
                Log.d(TAG, "showSyllabus: 周三" + showLessonBean.getDays().getW3())
                drawGrid(showLessonBean, showLessonBean.getDays().getW3(), 3)
            }
            if (showLessonBean.getDays().getW4() != null && showLessonBean.getDays().getW4() != "-") {
                Log.d(TAG, "showSyllabus: 周四" + showLessonBean.getDays().getW4())
                drawGrid(showLessonBean, showLessonBean.getDays().getW4(), 4)
            }
            if (showLessonBean.getDays().getW5() != null && showLessonBean.getDays().getW5() != "-") {
                Log.d(TAG, "showSyllabus: 周五" + showLessonBean.getDays().getW5())
                drawGrid(showLessonBean, showLessonBean.getDays().getW5(), 5)
            }
            if (showLessonBean.getDays().getW6() != null && showLessonBean.getDays().getW6() != "-") {
                Log.d(TAG, "showSyllabus: 周六" + showLessonBean.getDays().getW6())
                drawGrid(showLessonBean, showLessonBean.getDays().getW6(), 6)
            }
        }
    }

    private fun drawGrid(showLessonBean: Lesson, wString: String, start: Int) {
        val time: List<String> = wString.split("-")
        val lessonLinearLayout = LayoutInflater.from(activity).inflate(R.layout.lesson_grid, null, false) as MaterialRippleLayout
        val lessonRippleLayout: MaterialRippleLayout = lessonLinearLayout.findViewById(R.id.lessonInfoRipple) as MaterialRippleLayout
        lessonRippleLayout.foregroundGravity = Gravity.CENTER
        val lessonTextView = lessonLinearLayout.findViewById<TextView>(R.id.lessonTextView)
        lessonTextView.text = showLessonBean.getName().toString() + "\n@" + showLessonBean.getRoom()
        lessonTextView.width = gridWidth
        lessonTextView.height = gridHeight * (time[1].toInt() - time[0].toInt() + 1)
        val shape = resources.getDrawable(R.drawable.grid_background) as GradientDrawable
        shape.setColor(ColorUtils.setAlphaComponent(resources.getColor(showLessonBean.bgColor), 192))
        lessonTextView.setBackgroundDrawable(shape)
        val rowSpec = GridLayout.spec(time[0].toInt() - 1, time[1].toInt() - time[0].toInt() + 1)
        val columnSpec = GridLayout.spec(start)
        gridLayout.addView(lessonRippleLayout, GridLayout.LayoutParams(rowSpec, columnSpec))
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