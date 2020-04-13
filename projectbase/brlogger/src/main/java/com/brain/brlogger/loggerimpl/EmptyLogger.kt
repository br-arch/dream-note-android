package com.brain.brlogger.loggerimpl

import com.brain.brlogger.IBrLogger

/**
 * [IBrLogger]的空实现
 */
class EmptyLogger private constructor() : IBrLogger {
    override fun v(tag: String?, msg: String) {
        // Do nothing
    }

    override fun d(tag: String?, msg: String) {
        // Do nothing
    }

    override fun i(tag: String?, msg: String) {
        // Do nothing
    }

    override fun w(tag: String?, msg: String) {
        // Do nothing
    }

    override fun e(tag: String?, msg: String) {
        // Do nothing
    }

    class Builder {
        fun build() = EmptyLogger()
    }
}