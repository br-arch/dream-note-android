package com.brain.dreamnote.business.placeholder.di

import com.brain.arch.di.ActivityScope
import com.brain.arch.di.FragmentScope
import com.brain.dreamnote.business.placeholder.PlaceHolderActData
import com.brain.dreamnote.business.placeholder.PlaceHolderFragData
import dagger.Module
import dagger.Provides


@Module
class PlaceHolderModule {

    @ActivityScope
    @Provides
    fun providePlaceHolderActData() = PlaceHolderActData("place holder activity data")

    @FragmentScope
    @Provides
    fun providePlaceHolderFragData() = PlaceHolderFragData("place holder fragment data")

}