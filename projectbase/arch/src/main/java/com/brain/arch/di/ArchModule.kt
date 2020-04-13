package com.brain.arch.di

import com.brain.arch.arch.BaseDaggerApp
import com.brain.brlogger.di.BrLoggerModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    BaseViewModelModule::class,
    BrLoggerModule::class])
class ArchModule(private val app: BaseDaggerApp) {

    @Singleton
    @Provides
    fun provideApp(): BaseDaggerApp = app
}