package com.brain.brlogger.di

import com.brain.brlogger.IBrLogger
import com.brain.brlogger.loggerimpl.EmptyLogger
import com.brain.brlogger.loggerimpl.LogcatLogger
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class BrLoggerModule {

    @Singleton
    @Provides
    fun provideEmptyLogger(): EmptyLogger = IBrLogger.getEmptyLogger()

    @Singleton
    @Provides
    fun provideLogcatLogger(): LogcatLogger = IBrLogger.getLogcatLogger { "DreamNote" }
}