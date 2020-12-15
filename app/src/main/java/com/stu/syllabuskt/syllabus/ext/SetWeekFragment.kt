package com.stu.syllabuskt.syllabus.ext

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stu.syllabuskt.R

class SetWeekFragment : DialogFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var onItemClickListener: SetWeekAdapter.OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: SetWeekAdapter.OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context!!).inflate(R.layout.fragment_set_week, null, false).apply {
            recyclerView = findViewById(R.id.setWeekRecyclerView)
        }
        val setWeekAdapter = SetWeekAdapter(context!!).apply {
            setOnItemClickListener(onItemClickListener)
        }
        recyclerView.apply {
            adapter = setWeekAdapter
            layoutManager = GridLayoutManager(context!!, 5)
        }

        return AlertDialog.Builder(context!!)
            .setTitle("设定当前周")
            .setView(view)
            .setPositiveButton("取消", null)
            .create()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SetWeekFragment()
    }
}