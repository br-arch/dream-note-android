package com.brain.arch.arch

import com.brain.arch.di.ArchComponent
import com.brain.arch.di.ArchModule
import com.brain.arch.di.DaggerArchComponent

open class BaseDaggerApp : BaseApp() {

    lateinit var archComponent: ArchComponent

    override fun onCreate() {
        super.onCreate()
        init()
    }

    open fun init() {
        initDi()
    }

    open fun initDi() {
        archComponent = DaggerArchComponent.builder().archModule(ArchModule(this)).build()
    }
}