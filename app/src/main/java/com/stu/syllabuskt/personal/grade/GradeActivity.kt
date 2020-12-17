package com.stu.syllabuskt.personal.grade

import android.view.View
import android.widget.ImageView
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
    private lateinit var backIcon: ImageView
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

    private val refreshListener = object : GradePresenter.RefreshListener {
        override fun onFailure() {
            ToastUtil.showShort(this@GradeActivity, "加载失败")
            refreshGradeLayout.isRefreshing = false
        }

        override fun onSuccess() {
            ToastUtil.showLong(this@GradeActivity, "加载成功")
            refreshGradeLayout.isRefreshing = false
        }
    }

    override fun getContentView(): Int {
        return R.layout.activity_grade
    }

    override fun init() {
        super.init()
        presenter = GradePresenter(this, this)
        titleTV = findViewById<TextView>(R.id.titleBarTV).apply { text = "成绩" }
        backIcon = findViewById<ImageView>(R.id.backIcon).apply {
            visibility = View.VISIBLE
            setOnClickListener{ finish() }
        }
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
        presenter.getGrade(refreshListener)
        refreshGradeLayout.isRefreshing = true
        initEvent()
    }

    private fun initEvent() {
        refreshGradeLayout.setOnRefreshListener {
            presenter.getGrade(refreshListener)
        }
    }

    override fun setGradeDateAndShow(gradeArr: ArrayList<Grade>)  {
        val formattedData: ArrayList<ArrayList<GradeItemBean>> = dealWithData(gradeArr)
        for (i in 1..formattedData.size) {
            dealShowData(i, formattedData[i - 1])
        }
    }

    private fun dealWithData(gradeArr: ArrayList<Grade>): ArrayList<ArrayList<GradeItemBean>> {
        val sizeOfSemester = ArrayList<String>()    //有多少个学期，元素为学期名
        val buckets = ArrayList<ArrayList<GradeItemBean>>() //每个学期有多少个科目有成绩，元素第一项为学期信息，其余为该学期的科目成绩
        gradeArr.forEach {
            if (!sizeOfSemester.contains(it.semester)) {
                sizeOfSemester.add(it.semester)
            }
        }
        numOfSemester = sizeOfSemester.size
        sizeOfSemester.forEach { str ->
            val temp = ArrayList<GradeItemBean>()
            var flag = true
            for (i in 1..gradeArr.size) {
                if (gradeArr[i -1].semester == str) {   //同一个学期进同一个temp
                    if (flag) {
                        temp.add(GradeItemBean(str, "", GradeItem.Title))
                        flag = false
                    }
                    temp.add(GradeItemBean((gradeArr[i -1]).lessonName, gradeArr[i -1].grade.toString(), GradeItem.Content))
                }
            }
            buckets.add(temp)
        }
        return buckets
    }

    private fun dealShowData(i: Int, gradeItemBeanList: ArrayList<GradeItemBean>) {
        when (i) {
            1 -> {
                mGradeContainer1.visibility = View.VISIBLE
                mGradeContainer1.layoutManager = LinearLayoutManager(this)
                mGradeContainer1.adapter = GradeItemAdapter(this, gradeItemBeanList, gradeItemBeanList.size - 1)
            }
            2 -> {
                mGradeContainer2.visibility = View.VISIBLE
                mGradeContainer2.layoutManager = LinearLayoutManager(this)
                mGradeContainer2.adapter = GradeItemAdapter(this, gradeItemBeanList, gradeItemBeanList.size - 1)
            }
            3 -> {
                mGradeContainer3.visibility = View.VISIBLE
                mGradeContainer3.layoutManager = LinearLayoutManager(this)
                mGradeContainer3.adapter = GradeItemAdapter(this, gradeItemBeanList, gradeItemBeanList.size - 1)
            }
            4 -> {
                mGradeContainer4.visibility = View.VISIBLE
                mGradeContainer4.layoutManager = LinearLayoutManager(this)
                mGradeContainer4.adapter = GradeItemAdapter(this, gradeItemBeanList, gradeItemBeanList.size - 1)
            }
            5 -> {
                mGradeContainer5.visibility = View.VISIBLE
                mGradeContainer5.layoutManager = LinearLayoutManager(this)
                mGradeContainer5.adapter = GradeItemAdapter(this, gradeItemBeanList, gradeItemBeanList.size - 1)
            }
            6 -> {
                mGradeContainer6.visibility = View.VISIBLE
                mGradeContainer6.layoutManager = LinearLayoutManager(this)
                mGradeContainer6.adapter = GradeItemAdapter(this, gradeItemBeanList, gradeItemBeanList.size - 1)
            }
            7 -> {
                mGradeContainer7.visibility = View.VISIBLE
                mGradeContainer7.layoutManager = LinearLayoutManager(this)
                mGradeContainer7.adapter = GradeItemAdapter(this, gradeItemBeanList, gradeItemBeanList.size - 1)
            }
            8 -> {
                mGradeContainer8.visibility = View.VISIBLE
                mGradeContainer8.layoutManager = LinearLayoutManager(this)
                mGradeContainer8.adapter = GradeItemAdapter(this, gradeItemBeanList, gradeItemBeanList.size - 1)
            }
            9 -> {
                mGradeContainer9.visibility = View.VISIBLE
                mGradeContainer9.layoutManager = LinearLayoutManager(this)
                mGradeContainer9.adapter = GradeItemAdapter(this, gradeItemBeanList, gradeItemBeanList.size - 1)
            }
            10 -> {
                mGradeContainer10.visibility = View.VISIBLE
                mGradeContainer10.layoutManager = LinearLayoutManager(this)
                mGradeContainer10.adapter = GradeItemAdapter(this, gradeItemBeanList, gradeItemBeanList.size - 1)
            }
        }
    }
}
