package com.goddy.citystoresdemo.repository

import android.arch.lifecycle.LiveData
import com.goddy.citystoresdemo.utils.disk
import com.goddy.citystoresdemo.utils.network
import com.goddy.citystoresdemo.utils.ui
import com.goddy.citystoreslibrary.data.api.ApiService
import com.goddy.citystoreslibrary.data.db.CityDao
import com.goddy.citystoreslibrary.models.*
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRepository @Inject constructor(private val cityDao: CityDao, private val service: ApiService):CityDataSource{

    init{
        Timber.d("Repository Injected")
    }

    override fun loadCity(callbacks: CityDataSource.Callbacks) {
        network {
            try {

                val mResponse:Response<JSONObject> = service.fetchRemoteData().execute()
                if (mResponse.isSuccessful){

                    val listData = mutableListOf<City>()
                    val call = mResponse.body()
                    val jArray = call?.getJSONArray("cities")

                    for (i in 0 until jArray!!.length()) {
                        val objCity = jArray.getJSONObject(i)
                        val mCity = City()
                        mCity.id = objCity.getInt("id")
                        mCity.name = objCity.getString("name")
                        val arrayMall = objCity.getJSONArray("malls")
                        val mallList = mutableListOf<Mall>()
                        for (j in 0 until arrayMall!!.length()){
                            val objMall = arrayMall.getJSONObject(i)
                            val mMall = Mall()
                            mMall.cityId = mCity.id
                            mMall.id = objMall.getInt("id")
                            mMall.name = objMall.getString("name")

                            val arrayShop = objMall.getJSONArray("shops")
                            val shopList= mutableListOf<Shop>()
                            for(k in 0 until arrayShop!!.length()){
                                val objShop = arrayShop.getJSONObject(k)
                                val mShop = Shop(objShop.getInt("id"),mMall.id,objShop.getString("name"))
                                shopList.add(mShop)
                            }
                            mMall.shops = shopList
                            mallList.add(mMall)
                        }

                        listData.add(mCity)

                    }

                    disk {
                        cityDao.insertCity(listData)
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


    fun getCities(id:Int):LiveData<List<City>>{
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