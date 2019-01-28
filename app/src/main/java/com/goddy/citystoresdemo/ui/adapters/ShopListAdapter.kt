package com.goddy.citystoresdemo.ui.adapters

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.goddy.citystoresdemo.R
import com.goddy.citystoresdemo.databinding.ShopItemBinding
import com.goddy.citystoresdemo.utils.AppExecutors
import com.goddy.citystoreslibrary.models.Shop

/**
 * * A RecyclerView adapter for [Shop] class.
 */
class ShopListAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val shopClickCallback: ((Shop) -> Unit)?
) : DataBoundListAdapter<Shop, ShopItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<Shop>() {
            override fun areItemsTheSame(oldItem: Shop, newItem: Shop): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Shop, newItem: Shop): Boolean {
                return oldItem.name == newItem.name
            }
        }
) {
    override fun createBinding(parent: ViewGroup):ShopItemBinding {
        val binding = DataBindingUtil.inflate<ShopItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.shop_item,
                parent,
                false,
                dataBindingComponent
        )

        binding.root.setOnClickListener {
            binding.shop?.let {
                shopClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ShopItemBinding, item: Shop) {
        binding.shop = item
    }
}