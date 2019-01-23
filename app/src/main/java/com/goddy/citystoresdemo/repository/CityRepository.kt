package com.goddy.citystoresdemo.repository

import android.arch.lifecycle.LiveData
import com.goddy.citystoreslibrary.data.api.ApiResponse
import com.goddy.citystoreslibrary.data.api.ApiService
import com.goddy.citystoreslibrary.data.db.CityDao
import com.goddy.citystoreslibrary.models.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRepository @Inject constructor(private val cityDao: CityDao, private val service: ApiService){
    init{
        Timber.d("Repository Injected")
    }

    fun loadCity(): LiveData<Resource<List<City>>> {
        return object: NetworkBoundRepository<List<City>, List<City>>(){
            override fun saveFetchData(items: List<City>) {
                cityDao.insertCity(items)
            }

            override fun shouldFetch(data: List<City>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<City>> {
                return cityDao.fetchCities()
            }

            override fun fetchService(): LiveData<ApiResponse<List<City>>> {
                return service.fetchRemoteData()
            }

            override fun onFetchFailed(envelope: Envelope?) {
                Timber.d("onFetchFailed : $envelope")
            }

        }.asLiveData()
    }

    fun getCity(id:Int):LiveData<City>{
        return cityDao.getCity(id)
    }

    fun loadMalls(cityId:Int):LiveData<List<Mall>>{
        return cityDao.fetchMalls(cityId)
    }

    fun getMall(id: Int):LiveData<Mall>{
        return cityDao.getMall(id)
    }

    fun loadShops(mallId:Int):LiveData<List<Shop>>{
        return cityDao.fetchShops(mallId)
    }

    fun getShop(id: Int):LiveData<Shop>{
        return cityDao.getShop(id)
    }

}