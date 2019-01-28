package com.goddy.citystoresdemo.repository

import android.arch.lifecycle.LiveData
import com.goddy.citystoresdemo.utils.disk
import com.goddy.citystoresdemo.utils.network
import com.goddy.citystoresdemo.utils.ui
import com.goddy.citystoreslibrary.data.api.ApiService
import com.goddy.citystoreslibrary.data.api.WrapperResponse
import com.goddy.citystoreslibrary.data.db.CityDao
import com.goddy.citystoreslibrary.models.*
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRepository @Inject constructor(private val cityDao: CityDao, private val service: ApiService):CityDataSource{

    init{ Timber.d("Repository Injected") }

    override fun loadCity(callbacks: CityDataSource.Callbacks) {
        network {
            try {
                val mResponse:Response<WrapperResponse> = service.fetchRemoteData().execute()
                if (mResponse.isSuccessful){
                    val call = mResponse.body()
                    disk {
                        cityDao.insertCity(call!!.cities)
                        for(city in call!!.cities){
                            for(mall in city.malls){
                                mall.cityId = city.id
                                cityDao.insertMall(mall)

                                for (shop in mall.shops){
                                    shop.mallId = mall.id
                                    cityDao.insertShop(shop)
                                }
                            }
                        }
                        notifyOnSuccess(callbacks)
                    }
                }else{
                    notifyOnError(callbacks)
                }
            }catch (e:IOException){
                notifyOnError(callbacks)
                e.printStackTrace()
            }
        }
    }

    private fun notifyOnSuccess(callbacks: CityDataSource.Callbacks) {
        ui { callbacks.onSuccess() }
    }

    private fun notifyOnError(callbacks: CityDataSource.Callbacks) {
        ui { callbacks.onError() }
    }

    fun getCities():LiveData<List<City>>{
        return cityDao.fetchCities()
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