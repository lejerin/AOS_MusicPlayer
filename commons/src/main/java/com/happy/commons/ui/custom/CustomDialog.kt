package com.happy.commons.ui.custom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.R

class CustomDialog(context: Context, private val builder : Builder, private val isWrapContent : Boolean)
    : Dialog(context, R.style.Theme_Translucent_NoTitleBar) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lpWindow = WindowManager.LayoutParams().apply {
            flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dimAmount = builder.dimAmount
            if (isWrapContent) {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }
        window?.attributes = lpWindow
        setContentView(builder.layout)

        initDialogView()
        builder.otherSetting?.invoke(this)
    }

    private fun initDialogView() {
        builder.titleId?.let{
            findViewById<TextView>(it).text = builder.title
        }

        builder.positiveButtonId?.let{
            findViewById<View>(it)?.setOnClickListener{
                builder.positiveListener?.invoke(this)
                this.dismiss()
            }
        }

        builder.negativeButtonId?.let{
            findViewById<View>(it)?.setOnClickListener{
                builder.negativeListener?.invoke(this)
                this.dismiss()
            }
        }
    }

    override fun dismiss() {
        builder.dismissListener?.invoke(this)
        super.dismiss()
    }

    class Builder(private val context : Context, val layout : Int, private val isWrapContent : Boolean = true) {

        fun build() : CustomDialog = CustomDialog(context,this@Builder, isWrapContent)

        var dimAmount : Float = 0.4f // Dialog 제외한 background 영역 dim
            private set

        var titleId : Int? = null
            private set
        var title = ""
            private set

        var positiveButtonId : Int? = null
            private set
        var positiveListener: ((Dialog) -> Unit)? = null
            private set

        var negativeButtonId : Int? = null
            private set
        var negativeListener: ((Dialog) -> Unit)? = null
            private set

        var dismissListener: ((Dialog) -> Unit)? = null
            private set

        var otherSetting : ((Dialog) -> Unit)? = null
            private set

        fun setTitle(titleId : Int, titleText : String) : Builder {
            this.titleId = titleId
            this.title = titleText
            return this
        }

        fun setPositive(buttonId:Int, clickListener : (Dialog) -> Unit): Builder {
            this.positiveButtonId = buttonId
            this.positiveListener = clickListener
            return this
        }

        fun setNegative(buttonId:Int, clickListener : (Dialog) -> Unit): Builder {
            this.negativeButtonId = buttonId
            this.negativeListener = clickListener
            return this
        }

        fun setDismiss(dismissListener: (Dialog) -> Unit): Builder {
            this.dismissListener = dismissListener
            return this
        }

        fun setDim(dimAmount : Float) {
            this.dimAmount = dimAmount
        }

        fun setOtherSetting(settingLambda : (Dialog) -> Unit) {
            this.otherSetting = settingLambda
        }
    }
}