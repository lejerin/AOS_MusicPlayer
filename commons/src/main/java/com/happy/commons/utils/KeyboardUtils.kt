package com.happy.commons.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.showSoftKeyboard(view: View) {
    toggleSoftKeyboard(this, view, true)
}

fun Context.hideSoftKeyboard(view: View) {
    toggleSoftKeyboard(this, view, false)
}

private fun toggleSoftKeyboard(context: Context, view: View, isShow: Boolean) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (isShow) {
        imm.showSoftInput(view, 0)
    } else {
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

