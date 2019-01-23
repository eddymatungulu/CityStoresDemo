package com.goddy.citystoresdemo.ui.city

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.goddy.citystoresdemo.repository.CityRepository
import com.goddy.citystoreslibrary.models.City
import com.goddy.citystoreslibrary.models.FetchStatus
import com.goddy.citystoreslibrary.models.Resource
import com.goddy.citystoreslibrary.utils.AbsentLiveData
import javax.inject.Inject

class CityListViewModel @Inject constructor(var cityRepository: CityRepository): ViewModel(){
    private val load: MutableLiveData<String> = MutableLiveData()

    val cityLiveData: LiveData<Resource<List<City>>>

    var fetchStatus = FetchStatus()
        private set

    init {
        load.postValue("onLoad")
        cityLiveData = Transformations.switchMap(load){
            load.value?.let { cityRepository.loadCity() }
                ?: AbsentLiveData.create()
        }
    }

    fun fetchStatus(resource: Resource<List<City>>) {
        fetchStatus = resource.fetchStatus
    }

    fun loadNextPage() {

    }
}