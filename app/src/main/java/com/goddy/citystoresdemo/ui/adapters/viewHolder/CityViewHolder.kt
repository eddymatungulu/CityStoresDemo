package com.goddy.citystoresdemo.ui.adapters.viewHolder

import android.databinding.DataBindingUtil
import android.view.View
import com.goddy.citystoresdemo.databinding.CityItemBinding
import com.goddy.citystoreslibrary.models.City

class CityViewHolder(view:View, private val delegate:Delegate):BaseViewHolder(view){
    private lateinit var city: City
    private val binding by lazy { DataBindingUtil.bind<CityItemBinding>(view) }

    override fun bindData(data: Any) {
        if(data is City){
            city = data
            drawView()

        }
    }

    private fun drawView(){
        binding?.city = city
        binding?.executePendingBindings()
    }


    override fun onClick(v: View?) {
        delegate.onItemClick(city)
    }

    override fun onLongClick(v: View?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    interface Delegate{
       fun onItemClick(city: City)
   }
}