package com.brain.brlogger.loggerimpl

import android.util.Log
import com.brain.brlogger.IBrLogger
import com.brain.brlogger.IBrLogger.ITagHandler

/**
 * 显示日志在Logcat窗口的Logger
 *
 * Created by zero on 2020/3/8.
 */
class LogcatLogger private constructor(private val tagProducer: (() -> String?)? = null) : IBrLogger {

    private val defaultTag = "LoggerDefaultTag"

    private val tagHandler by TagHandlerLazy(tagProducer ?: { defaultTag })

    override fun v(tag: String?, msg: String): Unit = tagHandler.handleTag(tag).let { Log.v(it, msg) }

    override fun d(tag: String?, msg: String): Unit = tagHandler.handleTag(tag).let { Log.d(it, msg) }

    override fun i(tag: String?, msg: String): Unit = tagHandler.handleTag(tag).let { Log.i(it, msg) }

    override fun w(tag: String?, msg: String): Unit = tagHandler.handleTag(tag).let { Log.w(it, msg) }

    override fun e(tag: String?, msg: String): Unit = tagHandler.handleTag(tag).let { Log.e(it, msg) }

    class Builder {
        fun build(tagProducer: (() -> String?)? = null) = LogcatLogger(tagProducer)
    }

    class TagHandlerLazy(private val tagProducer: () -> String?) : Lazy<ITagHandler> {
        private var cached: ITagHandler? = null

        override val value: ITagHandler
            get() = cached ?: LogcatTagHandler(tagProducer)

        override fun isInitialized() = cached != null

    }

    class LogcatTagHandler(private val tagProducer: () -> String?) : ITagHandler {
        override fun handleTag(tag: String?): String? {
            return if (tag.isNullOrEmpty()) {
                return tagProducer()
            } else {
                tag
            }
        }
    }
}