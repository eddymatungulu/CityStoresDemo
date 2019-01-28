package com.goddy.citystoresdemo.ui.adapters

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.goddy.citystoresdemo.R
import com.goddy.citystoresdemo.databinding.CityItemBinding
import com.goddy.citystoresdemo.utils.AppExecutors
import com.goddy.citystoreslibrary.models.City


/**
 * * A RecyclerView adapter for [City] class.
 */
class CityListAdapter(
            private val dataBindingComponent: DataBindingComponent,
            appExecutors: AppExecutors,
            private val cityClickCallback: ((City) -> Unit)?
    ) : DataBoundListAdapter<City, CityItemBinding>(
            appExecutors = appExecutors,
            diffCallback = object : DiffUtil.ItemCallback<City>() {
                override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
                return oldItem.name == newItem.name

                }
            }
    ) {
        override fun createBinding(parent: ViewGroup): CityItemBinding {
            val binding = DataBindingUtil.inflate<CityItemBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.city_item,
                    parent,
                    false,
                    dataBindingComponent
            )

            binding.root.setOnClickListener {
                binding.city?.let {
                    cityClickCallback?.invoke(it)
                }
            }
            return binding
        }

        override fun bind(binding: CityItemBinding, item: City) {
            binding.city = item
        }
}
