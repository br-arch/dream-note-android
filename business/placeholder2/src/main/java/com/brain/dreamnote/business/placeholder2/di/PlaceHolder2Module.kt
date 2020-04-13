package com.brain.dreamnote.business.placeholder2.di

import com.brain.arch.di.ActivityScope
import com.brain.dreamnote.business.placeholder2.PlaceHolder2ActData
import dagger.Module
import dagger.Provides

@Module
class PlaceHolder2Module {

    @ActivityScope
    @Provides
    fun providePlaceHolderActData() = PlaceHolder2ActData("place holder 2 activity data")

}