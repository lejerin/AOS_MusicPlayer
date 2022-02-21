package com.etoos.commons.utils

import android.annotation.SuppressLint
import android.util.Log
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

object Logger {
    private val TAG = Logger::class.java.simpleName

    // Application 클래스에서 DEBUG_MODE 값 설정 필수.
    var DEBUG_MODE = false

    fun i(tag: String, message: String) {
        if (!DEBUG_MODE) { return }
        Log.i(tag, buildLogMsg(message))
    }

    fun e(tag: String, message: String) {
        if (!DEBUG_MODE) { return }
        Log.e(tag, buildLogMsg(message))
    }

    fun e(tag: String, message: String, e: Exception) {
        if (!DEBUG_MODE) { return }
        Log.e(tag, buildLogMsg(message), e)
    }

    fun d(tag: String, message: String) {
        if (!DEBUG_MODE) { return }
        Log.d(tag, buildLogMsg(message))
    }

    fun v(tag: String, message: String) {
        if (!DEBUG_MODE) { return }
        Log.v(tag, buildLogMsg(message))
    }

    fun w(tag: String, message: String) {
        if (!DEBUG_MODE) { return }
        Log.w(tag, buildLogMsg(message))
    }

    /**실행된 클래스 Name 과 메소드, 코드 라인에 대한 정보를 로그에 붙임. ex)  I/CrashlyticsLog: [BaseActivity.kt > initKollus > #72] initKollus start */
    private fun buildLogMsg(message: String): String {
        return try {
            var ste = Thread.currentThread().stackTrace[4]
            if (ste.fileName.equals("Logger.kt", ignoreCase = true)) {
                ste = Thread.currentThread().stackTrace[5] // saveLog 값이 디폴트로 설정될 경우, 스택이 한번 더 쌓이면서 5번째 index 정보를 가져와야함.
            }
            val sb = StringBuilder()
            sb.append("${getCurrentTime()} : ")
            sb.append("[")
            sb.append(ste.fileName)
            sb.append(" > ")
            sb.append(ste.methodName)
            sb.append(" > #")
            sb.append(ste.lineNumber)
            sb.append("] ")
            sb.append(message)
            sb.toString()
        } catch (e: Exception) {
            message
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentTime() : String {
        val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        return mFormat.format(Date())
    }
}