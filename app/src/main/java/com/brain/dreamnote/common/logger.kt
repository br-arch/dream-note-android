package com.brain.dreamnote.common

import com.brain.brlogger.IBrLogger

/**
 * 日志相关
 *
 * Created by zero on 2020/3/8.
 */

fun log(msg: String) = log(null, msg)

fun logV(msg: String) = logV(null, msg)

fun logD(msg: String) = logD(null, msg)

fun logI(msg: String) = logI(null, msg)

fun logW(msg: String) = logW(null, msg)

fun logE(msg: String) = logE(null, msg)

fun log(tag: String?, msg: String) = logD(tag, msg)

fun logV(tag: String?, msg: String) = brLogger.v(tag, msg)

fun logD(tag: String?, msg: String) = brLogger.d(tag, msg)

fun logI(tag: String?, msg: String) = brLogger.i(tag, msg)

fun logW(tag: String?, msg: String) = brLogger.w(tag, msg)

fun logE(tag: String?, msg: String) = brLogger.e(tag, msg)

val brLogger by createLoggerLazy()

fun createLoggerLazy(): Lazy<IBrLogger>? {
    return LoggerLazy()
}

class LoggerLazy : Lazy<IBrLogger> {

    private var cached: IBrLogger? = null

    override val value: IBrLogger
        get() {
            return cached ?: (if (isDebug()) IBrLogger.getLogcatLogger { "DreamNote" } else IBrLogger.getEmptyLogger())
                .also { cached = it }
        }

    override fun isInitialized() = cached != null
}
