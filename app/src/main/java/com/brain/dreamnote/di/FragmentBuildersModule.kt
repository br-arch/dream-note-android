package com.brain.dreamnote.di


import com.brain.dreamnote.ui.TestFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeTestFragment(): TestFragment

}
