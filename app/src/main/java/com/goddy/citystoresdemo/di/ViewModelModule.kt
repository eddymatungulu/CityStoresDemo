package com.goddy.citystoresdemo.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.goddy.citystoresdemo.ui.city.CityDetailViewModel
import com.goddy.citystoresdemo.ui.city.CityListViewModel
import com.goddy.citystoresdemo.ui.mall.MallDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CityListViewModel::class)
    abstract fun bindCityListViewModel(cityListViewModel: CityListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CityDetailViewModel::class)
    abstract fun bindCityDetailViewModel(cityDetailViewModel: CityDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MallDetailViewModel::class)
    abstract fun bindShopListViewModel(mallDetailViewModel: MallDetailViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: BaseViewModelFactory): ViewModelProvider.Factory
}
