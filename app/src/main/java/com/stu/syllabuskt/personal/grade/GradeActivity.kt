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
    private lateinit var recyclerViewContainer: RecyclerView

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
        recyclerViewContainer = findViewById(R.id.recyclerViewContainer)
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
        recyclerViewContainer.layoutManager = LinearLayoutManager(this)
        recyclerViewContainer.adapter = RecyclerViewContainerAdapter(this, dealWithData(gradeArr))

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
}
