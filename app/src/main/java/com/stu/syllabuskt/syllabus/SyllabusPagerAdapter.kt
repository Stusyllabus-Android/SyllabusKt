package com.stu.syllabuskt.syllabus

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Create by yuan on 2020/12/7
 */
class SyllabusPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    override fun getCount(): Int {
        return 20
    }

    override fun getItem(position: Int): Fragment {
        return SyllabusFragment.newInstance(position)
    }
}