package com.stu.syllabuskt.personal.theme

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.personal.theme.ThemePickerAdapter.OnItemClickListener

class ThemePickerFragment : DialogFragment() {

    private lateinit var mThemePickerRecyclerView: RecyclerView

    private lateinit var mOnItemClickListener: OnItemClickListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.fragment_theme_picker, null, false).apply {
            mThemePickerRecyclerView = findViewById(R.id.themePickerRecyclerView)
        }
        val mThemePickerAdapter = ThemePickerAdapter(context!!).apply {
            setOnItemClickListener(mOnItemClickListener)
        }
        mThemePickerRecyclerView.apply {
            adapter = mThemePickerAdapter
            layoutManager = GridLayoutManager(context!!, 5)
        }

        return AlertDialog.Builder(context!!)
            .setTitle("选择主题")
            .setView(view)
            .setPositiveButton("取消", null)
            .create()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThemePickerFragment()
    }
}