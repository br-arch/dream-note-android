package com.brain.arch.di

import androidx.lifecycle.ViewModelProvider
import com.brain.arch.arch.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class BaseViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}