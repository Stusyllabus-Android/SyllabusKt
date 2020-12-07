package com.stu.syllabuskt.main

import androidx.fragment.app.Fragment
import com.stu.syllabuskt.R
import com.stu.syllabuskt.oa.OAFragment
import com.stu.syllabuskt.personal.PersonalFragment
import com.stu.syllabuskt.syllabus.SyllabusFragment

/**
 *yuan
 *2020/9/6
 **/
class MainPresenter(val mainActivity: MainActivity) {
    init {
        arrayListOf<Fragment>().apply {
            this.add(SyllabusFragment.newInstance())
            this.add(OAFragment.newInstance())
            this.add(PersonalFragment.newInstance())
        }.let {
            mainActivity.apply {
                this.initBottomNavigationView(it)
                this.onTabItemSelected(it, R.id.syllabus)
            }
        }
    }
}