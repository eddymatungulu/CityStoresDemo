package com.goddy.citystoresdemo.ui.city

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.goddy.citystoresdemo.repository.CityDataSource
import com.goddy.citystoresdemo.repository.CityRepository
import com.goddy.citystoresdemo.utils.SingleLiveEvent
import com.goddy.citystoreslibrary.models.City
import javax.inject.Inject

class CityListViewModel @Inject constructor(var cityRepository: CityRepository): ViewModel(){

    val cityLive = SingleLiveEvent<Int>()
    val showErrorToast = SingleLiveEvent<Int>()

    init {
        cityRepository.loadCity(Callbacks())
    }

    fun loadData():LiveData<List<City>>{
        return cityRepository.getCities()
    }

    private inner class Callbacks : CityDataSource.Callbacks {
        override fun onSuccess() {
            cityLive.call()
        }

        override fun onError() {
            showErrorToast.call()
        }
    }

}