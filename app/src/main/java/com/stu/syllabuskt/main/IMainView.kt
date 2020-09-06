package com.stu.syllabuskt.main

import androidx.fragment.app.Fragment

/**
 *yuan
 *2020/9/6
 **/
interface IMainView {
    fun initBottomNavigationView(fragmentList: List<Fragment>)
    fun onTabItemSelected(fragmentList: List<Fragment>, id: Int)
}