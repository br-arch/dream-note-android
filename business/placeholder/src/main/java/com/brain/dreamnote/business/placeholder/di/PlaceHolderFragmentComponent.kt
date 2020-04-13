package com.brain.dreamnote.business.placeholder.di

import com.brain.arch.di.ArchComponent
import com.brain.arch.di.FragmentScope
import com.brain.dreamnote.business.placeholder.PlaceHolderFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ArchComponent::class], modules = [PlaceHolderModule::class])
interface PlaceHolderFragmentComponent {

    fun inject(fragment: PlaceHolderFragment)
}