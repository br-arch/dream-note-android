package com.brain.arch.di

import com.brain.arch.arch.BaseDaggerApp
import com.brain.brlogger.loggerimpl.EmptyLogger
import com.brain.brlogger.loggerimpl.LogcatLogger
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ArchModule::class])
interface ArchComponent {

    fun getApp(): BaseDaggerApp

    fun getEmptyLogger(): EmptyLogger

    fun getLogcatLogger(): LogcatLogger

    fun inject(app: BaseDaggerApp)

}