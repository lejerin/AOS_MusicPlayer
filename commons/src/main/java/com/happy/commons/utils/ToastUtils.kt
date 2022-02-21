package com.etoos.commons.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

object ToastUtils {

    var mToast: Toast? = null
    @SuppressLint("ShowToast")
    fun showCenterToast(context: Context?, message: String?) {
        context?.let { // null 이 아니면
            mToast?.cancel()
            mToast = Toast.makeText(context, message ?: "", Toast.LENGTH_SHORT)
            mToast?.show()
        }
    }

}