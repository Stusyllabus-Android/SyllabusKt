package com.stu.syllabuskt.main

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseActivity
import com.stu.syllabuskt.oa.OAContainerFragment
import com.stu.syllabuskt.personal.PersonalFragment
import com.stu.syllabuskt.syllabus.SyllabusContainerFragment
import com.stu.syllabuskt.utils.ActionTrigger
import com.stu.syllabuskt.widget.StuMainViewPager

class MainActivity : BaseActivity() {

    private val TAG = "MainActivity"

    lateinit var mainViewPager: StuMainViewPager

    val trigger = ActionTrigger(250)

    override fun init() {
        initBottomNavigationView()
        mainViewPager = findViewById(R.id.mainViewPager)
        val fragmentList = arrayListOf<Fragment>().apply {
            add(SyllabusContainerFragment.newInstance())
            add(OAContainerFragment.newInstance())
            add(PersonalFragment.newInstance())
        }
        mainViewPager.adapter = MainPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragmentList)
        mainViewPager.offscreenPageLimit = 2
    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    private fun initBottomNavigationView() {
        findViewById<BottomNavigationView>(R.id.navigation).setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                if (!trigger.canTrigger()) return false
                onTabItemSelected(p0.itemId)
                return true
            }
        })
    }

    private fun onTabItemSelected(id: Int) {
        when(id){
            R.id.syllabus -> 0
            R.id.oa -> 1
            R.id.personal -> 2
            else -> 0
        }.let {
            mainViewPager.currentItem = it
        }
    }
}
