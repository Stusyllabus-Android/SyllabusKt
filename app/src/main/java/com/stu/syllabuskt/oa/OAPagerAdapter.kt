package com.stu.syllabuskt.oa

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Create by yuan on 2020/12/5
 */
class OAPagerAdapter(private val fm: FragmentManager, private val behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    override fun getItem(position: Int): Fragment {
        return OAListFragment.newInstance(position + 1)
    }
}