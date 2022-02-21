package com.happy.commons.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object DialogUtils {

    /** 기본 다이얼로그 표시*/
    fun showDialog(
        context: Context?,
        title : String?,
        message: String?,
        positiveButton: String?,
        positiveAction: (() -> Unit)? = null,
        negativeButton: String? = null,
        negativeAction: (() -> Unit)? = null,
        cancelable: Boolean = false
    ): AlertDialog.Builder? {
        context?.let {
            it.getActivity()?.let { activity ->
                if (activity.isFinishing) { // 액티비티로 변환 가능한 경우 종료되었는지 체크.
                    return null
                }
            }
            AlertDialog.Builder(it).apply {
                setTitle(title)
                setMessage(message)
                setPositiveButton(positiveButton) { dialog, _ ->
                    dialog.dismiss()
                    positiveAction?.invoke()
                }
                negativeButton?.let {
                    setNegativeButton(negativeButton) { dialog, _ ->
                        dialog.dismiss()
                        negativeAction?.invoke()
                    }
                }
                setCancelable(cancelable)
                return this
            }
        }
        return null
    }
}