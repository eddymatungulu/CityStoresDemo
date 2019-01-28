package com.goddy.citystoresdemo.ui.city

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.goddy.citystoresdemo.repository.CityDataSource
import com.goddy.citystoresdemo.repository.CityRepository
import com.goddy.citystoresdemo.utils.SingleLiveEvent
import com.goddy.citystoreslibrary.models.City
import com.goddy.citystoreslibrary.models.Mall
import com.goddy.citystoreslibrary.models.Shop
import javax.inject.Inject

class CityDetailViewModel @Inject constructor(var cityRepository: CityRepository): ViewModel(){

    fun loadMallData(id:Int): LiveData<List<Mall>> {
        return cityRepository.loadMalls(id)
    }

    fun loadStore(list:List<Mall>):List<Shop>{
        val mList = mutableListOf<Shop>()
        for (mall in list){
            val items = cityRepository.loadShops(mall.id).value
            mList.addAll(items!!)
        }
        return mList
    }

}