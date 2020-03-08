package com.brain.brlogger

import com.brain.brlogger.loggerimpl.EmptyLogger
import com.brain.brlogger.loggerimpl.LogcatLogger

/**
 * 日志接口
 *
 * Created by zero on 2020/3/8.
 */
interface IBrLogger {

    fun v(msg: String) = v(null, msg)

    fun d(msg: String) = d(null, msg)

    fun i(msg: String) = i(null, msg)

    fun w(msg: String) = w(null, msg)

    fun e(msg: String) = e(null, msg)
    
    fun v(tag: String?, msg: String)

    fun d(tag: String?, msg: String)

    fun i(tag: String?, msg: String)

    fun w(tag: String?, msg: String)

    fun e(tag: String?, msg: String)

    companion object {

        fun getEmptyLogger() = EmptyLogger.Builder().build()

        fun getLogcatLogger(tagProducer: (() -> String?)? = null) = LogcatLogger.Builder().build(tagProducer)
    }

    /**
     * Tag处理相关接口
     */
    interface ITagHandler {
        fun handleTag(tag: String?): String?
    }
}