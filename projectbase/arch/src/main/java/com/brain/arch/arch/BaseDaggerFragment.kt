package com.brain.arch.arch

open class BaseDaggerFragment : BaseFragment() {

    fun getArchComponent() = (context?.applicationContext as? BaseDaggerApp)?.archComponent
}