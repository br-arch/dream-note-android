package com.brain.dreamnote.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brain.dreamnote.ui.TestViewModel
import com.brain.dreamnote.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestViewModel::class)
    abstract fun bindTestViewModel(testViewModel: TestViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
