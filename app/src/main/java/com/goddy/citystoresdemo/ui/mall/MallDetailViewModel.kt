package com.goddy.citystoresdemo.ui.mall

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.goddy.citystoresdemo.repository.CityRepository
import com.goddy.citystoreslibrary.models.Shop
import javax.inject.Inject

class MallDetailViewModel @Inject constructor(var cityRepository: CityRepository): ViewModel(){

    fun loadShopData(id:Int): LiveData<List<Shop>> {
        return cityRepository.loadShops(id)
    }

}