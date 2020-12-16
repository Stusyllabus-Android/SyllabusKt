package com.stu.syllabuskt.personal.grade

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseActivity
import com.stu.syllabuskt.utils.ToastUtil

class GradeActivity() : BaseActivity(), IGradeContract.IView {

    private lateinit var presenter: IGradeContract.IPresenter

    private lateinit var titleTV: TextView
    private lateinit var refreshGradeLayout: SwipeRefreshLayout
    private lateinit var mGradeContainer1: RecyclerView
    private lateinit var mGradeContainer2: RecyclerView
    private lateinit var mGradeContainer3: RecyclerView
    private lateinit var mGradeContainer4: RecyclerView
    private lateinit var mGradeContainer5: RecyclerView
    private lateinit var mGradeContainer6: RecyclerView
    private lateinit var mGradeContainer7: RecyclerView
    private lateinit var mGradeContainer8: RecyclerView
    private lateinit var mGradeContainer9: RecyclerView
    private lateinit var mGradeContainer10: RecyclerView

    private var numOfSemester = 0

    override fun getContentView(): Int {
        return R.layout.activity_grade
    }

    override fun init() {
        super.init()
        presenter = GradePresenter(this, this)
        titleTV = findViewById<TextView>(R.id.titleBarTV).apply { text = "成绩" }
        refreshGradeLayout = findViewById(R.id.refreshGradeLayout)
        mGradeContainer1 = findViewById(R.id.gradeContainer1)
        mGradeContainer2 = findViewById(R.id.gradeContainer2)
        mGradeContainer3 = findViewById(R.id.gradeContainer3)
        mGradeContainer4 = findViewById(R.id.gradeContainer4)
        mGradeContainer5 = findViewById(R.id.gradeContainer5)
        mGradeContainer6 = findViewById(R.id.gradeContainer6)
        mGradeContainer7 = findViewById(R.id.gradeContainer7)
        mGradeContainer8 = findViewById(R.id.gradeContainer8)
        mGradeContainer9 = findViewById(R.id.gradeContainer9)
        mGradeContainer10 = findViewById(R.id.gradeContainer10)
        presenter.getGrade(null)
        initEvent()
    }

    private fun initEvent() {
        refreshGradeLayout.setOnRefreshListener {
            presenter.getGrade(object : GradePresenter.RefreshListener {
                override fun onFailure() {
                    ToastUtil.showShort(this@GradeActivity, "刷新失败")
                    refreshGradeLayout.isRefreshing = false
                }

                override fun onSuccess() {
                    ToastUtil.showLong(this@GradeActivity, "刷新成功")
                    refreshGradeLayout.isRefreshing = false
                }
            })
        }
    }

    override fun setGradeDateAndShow(gradeArr: ArrayList<Grade>)  {
        mGradeContainer1.visibility = View.VISIBLE
        mGradeContainer1.layoutManager = LinearLayoutManager(this)
        mGradeContainer1.adapter = GradeItemAdapter(this, ArrayList<GradeItemBean>().apply {
            add(GradeItemBean("2019-2020学年春季学期", "", GradeItem.Title))
            add(GradeItemBean(("古代戏曲经典专题研究"), "86", GradeItem.Content))
        })
//        dealWithData(gradeArr)
    }

//    private fun dealWithData(gradeArr: ArrayList<Grade>): ArrayList<ArrayList<GradeItemBean>> {
//        var sizeSemester = ArrayList<String>()
//        val buckets = ArrayList<ArrayList<GradeItemBean>>()
//        gradeArr.forEach {
//            if (!sizeSemester.contains(it.semester)) {
//                sizeSemester.add(it.semester)
//            }
//        }
//        numOfSemester = sizeSemester.size
//        sizeSemester.forEach { str ->
//            val temp = ArrayList<GradeItemBean>()
//            gradeArr.forEach { grade ->
//                2019-2020学年春季学期课程:古代戏曲经典专题研究成绩:86
//                if (grade.semester == str) temp.add()
//            }
//        }
//    }
}
