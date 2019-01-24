package com.goddy.citystoresdemo.ui.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.goddy.citystoresdemo.R
import com.goddy.citystoresdemo.databinding.CityItemBinding
import com.goddy.citystoreslibrary.models.City

class CityListAdapter: RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    private lateinit var cityList:List<City>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListAdapter.ViewHolder {
        val binding  = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.city_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityListAdapter.ViewHolder, position: Int) {
        holder.bind(cityList[position])
    }

    override fun getItemCount(): Int {
        return if(::cityList.isInitialized) cityList.size else 0
    }

    fun updateCityList(cityList:List<City>){
        this.cityList = cityList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: CityItemBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = PostViewModel()

        fun bind(city:City){
            viewModel.bind(city)
            binding.viewModel = viewModel
        }
    }
}