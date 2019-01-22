package com.goddy.citystoresdemo.ui.city

import android.arch.lifecycle.ViewModel
import com.goddy.citystoresdemo.repository.CityRepository
import javax.inject.Inject

class CityDetailViewModel @Inject constructor(var cityRepository: CityRepository): ViewModel(){

}