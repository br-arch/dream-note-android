package com.brain.dreamnote.business.placeholder.di

import com.brain.arch.di.ActivityScope
import com.brain.arch.di.ArchComponent
import com.brain.dreamnote.business.placeholder.PlaceHolderActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ArchComponent::class], modules = [PlaceHolderModule::class])
interface PlaceHolderActivityComponent {

    fun inject(activity: PlaceHolderActivity)
}