package com.etoos.commons.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.getString(@StringRes resId: Int?) =
    resId?.let {
        getString(it)
    } ?: run {
        ""
    }

fun Context.getDrawable(@DrawableRes resId: Int?) =
    resId?.let { ContextCompat.getDrawable(this, it) }

fun Context.getColor(@ColorRes resId: Int?) =
    resId?.let { ContextCompat.getColor(this, it) }

/* context -> activity */
fun Context.getActivity(): Activity? =
    when (this) {
        is Activity -> this
        is ContextWrapper -> this.baseContext.getActivity()
        else -> null
    }