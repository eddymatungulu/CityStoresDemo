package com.goddy.citystoresdemo.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.goddy.citystoreslibrary.data.api.ApiResponse
import com.goddy.citystoreslibrary.models.Envelope
import com.goddy.citystoreslibrary.models.Resource
import timber.log.Timber

abstract class NetworkBoundRepository<ResultType, RequestType>
internal constructor() {

    private val result: MediatorLiveData<Resource<ResultType>> = MediatorLiveData()

    init {
        Timber.d("Injection NetworkBoundRepository")
        result.postValue(Resource.loading(null, null))
        val loadedFromDB = loadFromDb()
        result.addSource(loadedFromDB) { data ->
            result.removeSource(loadedFromDB)
            if (shouldFetch(data)) {
                fetchFromNetwork(loadedFromDB)
            } else {
                result.addSource<ResultType>(loadedFromDB) { newData -> setValue(Resource.success(newData, 1)) }
            }
        }
    }

    private fun fetchFromNetwork(loadedFromDB: LiveData<ResultType>) {
        val apiResponse = fetchService()
        result.addSource(loadedFromDB) { newData -> setValue(Resource.loading(newData, 1)) }
        result.addSource(apiResponse) { response ->
            response?.let {
                when (response.isSuccessful) {
                    true -> {
                        response.body?.let {
                            saveFetchData(it)
                            val loaded = loadFromDb()
                            result.addSource(loaded) { newData ->
                                newData?.let {
                                    setValue(Resource.success(newData, response.nextPage))
                                }
                            }
                        }
                    }
                    false -> {
                        onFetchFailed(response.envelope)
                        response.envelope?.let {
                            result.removeSource(loadedFromDB)
                            result.addSource<ResultType>(loadedFromDB) { newData ->
                                setValue(Resource.error(it.message, newData))
                            }
                        }
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        result.value = newValue
    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @WorkerThread
    protected abstract fun saveFetchData(items: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun fetchService(): LiveData<ApiResponse<RequestType>>

    @MainThread
    protected abstract fun onFetchFailed(envelope: Envelope?)
}