package com.brain.dreamnote.middleware.placeholder.di

import androidx.lifecycle.ViewModel
import com.brain.arch.di.ViewModelKey
import com.brain.dreamnote.middleware.placeholder.PlaceHolderViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PlaceHolderViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PlaceHolderViewModel::class)
    abstract fun bindPlaceHolderViewModel(viewModel: PlaceHolderViewModel): ViewModel
}