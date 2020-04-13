package com.brain.dreamnote.business.placeholder2.di

import com.brain.arch.di.ActivityScope
import com.brain.arch.di.ArchComponent
import com.brain.dreamnote.business.placeholder2.PlaceHolder2Activity
import dagger.Component

@ActivityScope
@Component(dependencies = [ArchComponent::class], modules = [PlaceHolder2Module::class])
interface PlaceHolder2ActivityComponent {

    fun inject(activity: PlaceHolder2Activity)
}