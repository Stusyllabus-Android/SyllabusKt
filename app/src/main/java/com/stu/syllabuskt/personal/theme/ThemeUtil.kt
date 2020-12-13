package com.stu.syllabuskt.personal.theme

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.stu.syllabuskt.App
import com.stu.syllabuskt.R

/**
 * Create by yuan on 2020/12/13
 */
object ThemeUtil {

    private val TAG = "ThemeUtil"

    private const val THEME_SP_NAME = "THEME"
    private const val THEME_SP_KEY = "themeName"

    private var style: Int? = null
    private var sp: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    var mThemeBeanList: ArrayList<ThemeBean> = ArrayList<ThemeBean>()
    var mThemeBeanMap: Map<String, ThemeBean> = HashMap<String, ThemeBean>()

    init {
        mThemeBeanList.apply {
            add(ThemeBean("blue", R.color.material_blue_500, R.style.AppTheme))
            add(ThemeBean("lightBlue", R.color.material_lightBlue_500, R.style.AppThemeLightBlue))
            add(ThemeBean("indigo", R.color.material_indigo_500, R.style.AppThemeIndigo))
            add(ThemeBean("cyan", R.color.material_cyan_700, R.style.AppThemeCyan))
            add(ThemeBean("teal", R.color.material_teal_500, R.style.AppThemeTeal))
            add(ThemeBean("red", R.color.material_red_500, R.style.AppThemeRed))
            add(ThemeBean("pink", R.color.material_pink_500, R.style.AppThemePink))
            add(ThemeBean("purple", R.color.material_purple_500, R.style.AppThemePurple))
            add(ThemeBean("deepPurple", R.color.material_deepPurple_500, R.style.AppThemeDeepPurple))
            add(ThemeBean("green", R.color.material_green_500, R.style.AppThemeGreen))
            add(ThemeBean("lightGreen", R.color.material_lightGreen_500, R.style.AppThemeLightGreen))
            add(ThemeBean("lime", R.color.material_Lime_700, R.style.AppThemeLime))
            add(ThemeBean("classic", R.color.colorClassic, R.style.AppThemeClassic))
            add(ThemeBean("bili", R.color.colorBili, R.style.AppThemeBili))
            add(ThemeBean("orange", R.color.material_orange_500, R.style.AppThemeOrange))
            add(ThemeBean("deepOrange", R.color.material_deepOrange_500, R.style.AppThemeDeepOrange))
            add(ThemeBean("brown", R.color.material_brown_500, R.style.AppThemeBrown))
            add(ThemeBean("grey", R.color.material_grey_500, R.style.AppThemeGrey))
            add(ThemeBean("blueGrey", R.color.material_blueGrey_500, R.style.AppThemeBlueGrey))
            add(ThemeBean("black", R.color.colorBlack, R.style.AppThemeBlack))
        }.forEach {
            (mThemeBeanMap as HashMap<String, ThemeBean>)[it.name] = it
        }

        sp = App.getContext().getSharedPreferences(THEME_SP_NAME, Context.MODE_PRIVATE)
        style = mThemeBeanMap[sp?.getString(THEME_SP_KEY, "classic") ?: "classic"]?.styleRec ?: R.style.AppThemeClassic
    }

    fun getMCurrentThemeName(): String = sp?.getString(THEME_SP_KEY, "classic") ?: "classic"

    fun getStyle(): Int{
        Log.i(TAG, "style is $style")
        return style ?: mThemeBeanList[12].styleRec
    }

    fun setStyleName(name: String) {
        this.style = mThemeBeanMap[name]?.styleRec
        editor = sp?.edit()
        editor?.putString(THEME_SP_KEY, name)
        editor?.apply()
    }

    data class ThemeBean(val name: String, val colorPrimary: Int, val styleRec: Int)
}