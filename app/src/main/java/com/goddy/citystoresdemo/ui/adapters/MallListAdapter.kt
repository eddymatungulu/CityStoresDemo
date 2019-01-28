package com.goddy.citystoresdemo.ui.adapters

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.goddy.citystoresdemo.R
import com.goddy.citystoresdemo.databinding.MallItemBinding
import com.goddy.citystoresdemo.utils.AppExecutors
import com.goddy.citystoreslibrary.models.Mall

/**
 * * A RecyclerView adapter for [Mall] class.
 */
class MallListAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val mallClickCallback: ((Mall) -> Unit)?
) : DataBoundListAdapter<Mall, MallItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<Mall>() {
            override fun areItemsTheSame(oldItem: Mall, newItem: Mall): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Mall, newItem: Mall): Boolean {
                return oldItem.name == newItem.name

            }
        }
) {
    override fun createBinding(parent: ViewGroup):MallItemBinding {
        val binding = DataBindingUtil.inflate<MallItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.mall_item,
                parent,
                false,
                dataBindingComponent
        )

        binding.root.setOnClickListener {
            binding.mall?.let {
                mallClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: MallItemBinding, item: Mall) {
        binding.mall = item
    }
}