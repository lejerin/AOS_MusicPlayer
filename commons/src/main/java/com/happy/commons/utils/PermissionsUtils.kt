package com.happy.commons.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat

/* 권한 체크 & 권한 요청 */
fun Context.checkPermissionsAndRequest(
    permissions: Array<String>,
    request: ActivityResultLauncher<Array<String>>
): Boolean {
    val result = hasPermissions(permissions)
    if (!result) {
        request.launch(permissions)
    }
    return result
}

/* 권한 체크 */
fun Context.hasPermissions(permissions: Array<String>): Boolean =
    permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }