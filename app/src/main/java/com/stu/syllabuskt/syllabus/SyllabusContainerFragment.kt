package com.stu.syllabuskt.syllabus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.base.BaseFragment
import com.stu.syllabuskt.syllabus.ext.SetWeekAdapter
import com.stu.syllabuskt.syllabus.ext.SetWeekFragment
import com.stu.syllabuskt.utils.ToastUtil
import com.stu.syllabuskt.widget.CtlScrollViewPager

class SyllabusContainerFragment : BaseFragment() {

    private val TAG = "SyllabusContainerFragment"

    private var selectedWeek = 1

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var menuIV: ImageView
    private lateinit var titleTV: TextView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var syllabusContainer: CtlScrollViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_syllabus_container, container, false).apply {
            drawerLayout = findViewById(R.id.nav_syllabus_drawerlayout)
            navigationView = findViewById(R.id.nav_syllabus)
            syllabusContainer = findViewById(R.id.syllabusContainer)
            menuIV = findViewById<ImageView>(R.id.menuIcon).apply { visibility = View.VISIBLE }
            titleTV = findViewById<TextView>(R.id.titleBarTV).apply{
                text = "第 $selectedWeek 周"
            }
            initEvent()
        }
    }

    private fun initEvent() {
        menuIV.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.setWeek -> setCurWeek()
                R.id.addLesson -> startAddLesson()
                R.id.deleteLesson -> startDeleteLesson()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        syllabusContainer.adapter = SyllabusPagerAdapter(
            childFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        syllabusContainer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.i(TAG, "onPageScrolled() >>> $position")
            }

            override fun onPageSelected(position: Int) {
                Log.i(TAG, "onPageSelected() >>> $position")
                selectedWeek = position + 1
                titleTV.text = "第 ${position + 1} 周"
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.i(TAG, "onPageScrollStateChanged() >>> $state")
            }
        })
        val setWeek = StuContext.getSharePreferences(context!!).getInt(SetWeekKey, -1)
        if (setWeek != -1) {
            syllabusContainer.currentItem = StuContext.getSharePreferences(context!!).getInt(SetWeekKey, -1)
            Log.i(TAG, "current week has set, is ${setWeek + 1}")
        }
    }

    private fun setCurWeek() {
        SetWeekFragment().apply {
            setOnItemClickListener(object : SetWeekAdapter.OnItemClickListener {
                override fun onItemClickListener(position: Int) {
                    syllabusContainer.currentItem = position
                    StuContext.getSharePreferences(context!!).edit().putInt(SetWeekKey, position).apply()
                    Log.i(TAG, "onItemClickListener() >> position is $position")
                    dismiss()
                }
            })
        }.show(childFragmentManager, "SetWeekFragment")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_LESSON_CODE && data?.getStringExtra("hadAddNewLesson") == "hadAddNewLesson") {
            ToastUtil.showShort(context, "刷新可展示最新课程数据")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SyllabusContainerFragment()

        const val ADD_LESSON_CODE = 1
        const val SetWeekKey = "SetWeekKey"
        const val CurrentSemesterKey = "CurrentSemester"
    }
}
