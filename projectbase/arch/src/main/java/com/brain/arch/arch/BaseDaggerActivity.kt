package com.brain.arch.arch

open class BaseDaggerActivity : BaseActivity() {

    fun getArchComponent() = (application as? BaseDaggerApp)?.archComponent
}