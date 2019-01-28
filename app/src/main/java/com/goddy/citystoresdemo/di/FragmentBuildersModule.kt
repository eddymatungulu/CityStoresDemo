package com.goddy.citystoresdemo.di
import com.goddy.citystoresdemo.ui.city.CityDetailFragment
import com.goddy.citystoresdemo.ui.city.CityListFragment
import com.goddy.citystoresdemo.ui.mall.MallDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeCityListFragment(): CityListFragment

    @ContributesAndroidInjector
    abstract fun contributeCityDetailFragment(): CityDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeShopListFragment(): MallDetailFragment

}
