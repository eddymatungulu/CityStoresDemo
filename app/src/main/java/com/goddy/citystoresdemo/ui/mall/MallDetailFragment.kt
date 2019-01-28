package com.goddy.citystoresdemo.ui.mall

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.goddy.citystoresdemo.R
import com.goddy.citystoresdemo.binding.FragmentDataBindingComponent
import com.goddy.citystoresdemo.databinding.FragmentShopListBinding
import com.goddy.citystoresdemo.di.Injectable
import com.goddy.citystoresdemo.ui.adapters.ShopListAdapter
import com.goddy.citystoresdemo.utils.AppExecutors
import com.goddy.citystoresdemo.utils.autoCleared
import com.goddy.citystoreslibrary.models.Mall
import kotlinx.android.synthetic.main.fragment_city_detail.*
import kotlinx.android.synthetic.main.fragment_shop_detail.*
import kotlinx.android.synthetic.main.fragment_shop_list.*
import javax.inject.Inject


class MallDetailFragment : Fragment(),Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var binding by autoCleared<FragmentShopListBinding>()

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var adapter by autoCleared<ShopListAdapter>()

    lateinit var mallDetailViewModel: MallDetailViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                com.goddy.citystoresdemo.R.layout.fragment_shop_list,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mallDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(MallDetailViewModel::class.java)
        initRecyclerView()
        val rvAdapter = ShopListAdapter(
                dataBindingComponent = dataBindingComponent,
                appExecutors = appExecutors
        ) {
            shop -> run{
            val bundle = Bundle()
            bundle.putString("shop",shop.name)
            NavHostFragment.findNavController(this@MallDetailFragment).navigate(R.id.action_shopListFragment_to_shopDetailFragment, bundle, NavOptions.Builder().setPopUpTo(R.id.shopListFragment, true).build())
            }
        }
        binding.shopList.adapter = rvAdapter
        adapter = rvAdapter

        backHomeL.setOnClickListener { View.OnClickListener {
            Navigation.findNavController(activity!!,R.id.container).navigateUp()
        } }
    }

    override fun onResume() {
        super.onResume()
        val mall = arguments?.getParcelable<Mall>("mall")
        shopNameL.text = mall!!.name

        mallDetailViewModel.loadShopData(mall.id).observe(this, Observer {
            adapter.submitList(it)
        })

    }
    private fun initRecyclerView() {
        shopList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            }
        })
    }


}