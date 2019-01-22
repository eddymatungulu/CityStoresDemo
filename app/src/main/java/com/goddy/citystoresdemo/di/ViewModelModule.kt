package com.goddy.citystoresdemo.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.goddy.citystoresdemo.ui.city.CityDetailViewModel
import com.goddy.citystoresdemo.ui.city.CityListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CityListViewModel::class)
    abstract fun bindRepoListViewModel(cityListViewModel: CityListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CityDetailViewModel::class)
    abstract fun bindRepoDetailViewModel(cityDetailViewModel: CityDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: BaseViewModelFactory): ViewModelProvider.Factory
}
