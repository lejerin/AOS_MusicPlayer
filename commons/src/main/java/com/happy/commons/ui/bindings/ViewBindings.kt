package com.etoos.commons.ui.bindings

import android.view.View
import androidx.databinding.BindingAdapter

@set:BindingAdapter("visibleGone")
var View.visibleGone
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

@set:BindingAdapter("visibleInvisible")
var View.visibleInvisible
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.INVISIBLE
    }

@set:BindingAdapter("select")
var View.select
    get() = isSelected
    set(value) {
        isSelected = value
    }

private var delayTime = 0
@set:BindingAdapter("delay")
var View.delay
    get() = delayTime
    set(value) {
        delayTime = value
    }

/* View.delay 시간동안 중복 클릭 방지*/
@BindingAdapter("onSafeClick")
fun View.setOnSafeClickListener(onSafeClick: (() -> Unit)?) {
    onSafeClick?.let {
        setOnClickListener(object : View.OnClickListener {
            var lastClickTime = 0L
            override fun onClick(v: View?) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime < delay) {
                    return
                }
                lastClickTime = currentTime
                it.invoke()
            }
        })
    }
}