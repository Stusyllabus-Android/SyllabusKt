package com.stu.syllabuskt.main

import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseActivity
import com.stu.syllabuskt.utils.ActionTrigger

class MainActivity : BaseActivity(), IMainView {

    val TAG = "MainActivity"

    lateinit var mainPresenter: MainPresenter

    val trigger = ActionTrigger(250)

    override fun init() {
        mainPresenter = MainPresenter(this)
    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initBottomNavigationView(fragmentList: List<Fragment>) {
        findViewById<BottomNavigationView>(R.id.navigation).setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                if (!trigger.canTrigger()) return false
                onTabItemSelected(fragmentList, p0.itemId)
                return true
            }
        })
    }

    override fun onTabItemSelected(fragmentList: List<Fragment>, id: Int) {
        when(id){
            R.id.syllabus -> fragmentList[0]
            R.id.oa -> fragmentList[1]
            R.id.person -> fragmentList[2]
            else -> fragmentList[0]
        }.let {
            supportFragmentManager.beginTransaction().replace(R.id.mainContainer, it).commit()
        }
    }
}
