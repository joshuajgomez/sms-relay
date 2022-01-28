package com.joshgm3z.smsrelay.utils

import android.util.Log
import java.lang.Exception

object Logger {
    private const val TAG = "[JOSH-GOM3Z] "
    fun entryLog() {
        val element = Thread.currentThread().stackTrace[3]
        var className = element.className
        className = className.substring(className.lastIndexOf(".") + 1, className.length)
        val methodName = element.methodName
        Log.v(TAG + className, "$methodName >>> Entry")
    }

    fun exitLog() {
        val element = Thread.currentThread().stackTrace[3]
        var className = element.className
        className = className.substring(className.lastIndexOf(".") + 1, className.length)
        val methodName = element.methodName
        Log.v(TAG + className, "$methodName <<< Exit")
    }

    fun log(message: String) {
        val element = Thread.currentThread().stackTrace[3]
        var className = element.className
        className = className.substring(className.lastIndexOf(".") + 1, className.length)
        val methodName = element.methodName
        Log.i(TAG + className, "$methodName : $message")
    }

    fun log(logPriority: Int, message: String?) {
        val element = Thread.currentThread().stackTrace[3]
        var className = element.className
        className = className.substring(className.lastIndexOf(".") + 1, className.length)
        val methodName = element.methodName
        Log.println(logPriority, TAG + className + "#" + methodName, message!!)
    }

    fun log(logPriority: Int, tag: String, message: String?) {
        val element = Thread.currentThread().stackTrace[3]
        var className = element.className
        className = className.substring(className.lastIndexOf(".") + 1, className.length)
        val methodName = element.methodName
        Log.println(logPriority, TAG + className + "#" + methodName + " : " + tag, message!!)
    }

    fun exceptionLog(e: Exception) {
        val element = Thread.currentThread().stackTrace[3]
        var className = element.className
        className = className.substring(className.lastIndexOf(".") + 1, className.length)
        val methodName = element.methodName
        Log.println(Log.ASSERT, TAG + className, methodName + " Exception : " + e.message)
    }
}