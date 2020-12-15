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
class TipDialog private constructor(context: Context, private val builder: Builder) : Dialog(context) {

    private lateinit var titleTV: TextView
    private lateinit var contentTV: TextView

    init {
        setContentView(R.layout.tip_dialog)
        titleTV = findViewById(R.id.title)
        contentTV = findViewById(R.id.content)
        window?.setGravity(Gravity.CENTER)
    }

    override fun show() {
        titleTV.text = builder.title
        contentTV.text = builder.content
        findViewById<TextView>(R.id.negative).apply {
            text = builder.negative
            setOnClickListener { v ->
                builder.negativeListener?.onClick(v)
                dismiss()
            }
        }
        findViewById<TextView>(R.id.positive).apply {
            text = builder.positive
            setOnClickListener { v ->
                builder.positiveListener?.onClick(v)
                dismiss()
            }
        }
        super.show()
    }

    class Builder(val context: Context) {
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
            TipDialog(context, this).apply {
                setCanceledOnTouchOutside(false)
            }.show()
        }
    }
}