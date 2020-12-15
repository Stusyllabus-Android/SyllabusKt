package com.stu.syllabuskt.widget

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.stu.syllabuskt.R

/**
 * Create by yuan on 2020/12/15
 */
class TipDialog private constructor(context: Context, builder: Builder) : Dialog(context) {

    init {
        setContentView(R.layout.tip_dialog)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(LayoutUtil.getScreenWidth(context) / 3 * 2, LinearLayout.LayoutParams.WRAP_CONTENT)
        findViewById<TextView>(R.id.title).text = builder.title
        findViewById<TextView>(R.id.content).text = builder.content
        findViewById<TextView>(R.id.negative).apply {
            text = builder.negative
            setOnClickListener(builder.negativeListener)
        }
        findViewById<TextView>(R.id.positive).apply {
            text = builder.positive
            setOnClickListener(builder.positiveListener)
        }
    }

    inner class  Builder {

        var title: String? = null
        var content: String? = null

        var negative = "取消"
        var positive = "确定"

        var positiveListener: View.OnClickListener? = null
        var negativeListener: View.OnClickListener? = null

        fun title(title: String): Builder {
            this.title = title
            return this
        }

        fun content(content: String): Builder {
            this.content = content
            return this
        }

        fun negative(negative: String): Builder {
            this.negative
            return this
        }

        fun positive(positive: String): Builder {
            this.positive = positive
            return this
        }

        fun setPositiveClickListener(onClickListener: View.OnClickListener): Builder {
            positiveListener = onClickListener
            return this
        }

        fun setNegativeClickListener(onClickListener: View.OnClickListener): Builder {
            negativeListener = onClickListener
            return this
        }

        fun show() {
            setCanceledOnTouchOutside(false)
            TipDialog(context, this).show()
        }
    }
}