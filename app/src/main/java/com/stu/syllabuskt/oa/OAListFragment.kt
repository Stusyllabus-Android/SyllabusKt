package com.stu.syllabuskt.oa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseFragment
import com.stu.syllabuskt.bean.OABean
import com.stu.syllabuskt.utils.ToastUtil
import com.stu.syllabuskt.widget.LoadingDialog

private const val POSITION_PARAM = "position_param"

class OAListFragment : BaseFragment(), OAListContract.view {

    private val TAG = "OAListFragment"

    private var position: Int? = null

    private lateinit var oaRefreshLayout: SwipeRefreshLayout
    private lateinit var oaListContainer: RecyclerView
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var oaListPresenter: OAListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(POSITION_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadingDialog = LoadingDialog(context!!, null)
        oaListPresenter = OAListPresenter(this, context!!)
        return inflater.inflate(R.layout.fragment_oa_list, container, false).apply {
            oaRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.oaRefreshLayout)
            oaListContainer = findViewById(R.id.oaListContainer)
            oaListContainer.layoutManager = LinearLayoutManager(context!!)
            oaListPresenter.loadOAList(position ?: 1, null)
            initEvent()
        }
    }

    private fun initEvent() {
        oaRefreshLayout.setOnRefreshListener {
            oaListPresenter.loadOAList(position ?: 1, object : OAListPresenter.RefreshListener {
                override fun onSuccess() {
                    ToastUtil.showShort(context!!, "刷新成功")
                    oaRefreshLayout.isRefreshing = false
                }

                override fun onFailure() {
                    ToastUtil.showShort(context!!, "刷新失败")
                    oaRefreshLayout.isRefreshing = false
                }
            })
        }
    }

    override fun showLoading() {
//        runOnUiThread { loadingDialog.show() }
    }

    override fun showErrMsg(msg: String) {
//        ToastUtil.showShort(context!!, msg)
    }

    override fun setPagerAdapter(oaList: List<OABean>?) {
        runOnUiThread {
//            loadingDialog.realDismiss()
            context?.let {
                oaListContainer.adapter = OAListAdapter(it, oaList)
                oaListContainer.run { adapter?.notifyDataSetChanged() }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(position: Int) =
            OAListFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION_PARAM, position)
                }
            }
    }
}