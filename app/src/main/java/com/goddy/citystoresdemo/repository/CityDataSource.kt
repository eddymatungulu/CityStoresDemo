package com.goddy.citystoresdemo.repository

import android.support.annotation.MainThread

interface CityDataSource {
    fun loadCity(callbacks: Callbacks)

    interface Callbacks {
        @MainThread
        fun onSuccess()

        @MainThread
        fun onError()
    }
}