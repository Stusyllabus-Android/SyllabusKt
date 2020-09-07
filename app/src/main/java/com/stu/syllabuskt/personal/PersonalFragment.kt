package com.stu.syllabuskt.personal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stu.syllabuskt.R

class PersonalFragment : Fragment() {

    private lateinit var dispatcher: IDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dispatcher = PersonalDispatcher(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_personal, container, false)
        dispatcher.onCreateView(rootView)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        dispatcher.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        dispatcher.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && data?.getStringExtra("finishMainActivity") == "finishMainActivity")
            toLoginView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = PersonalFragment()
    }
}
