package com.goddy.citystoresdemo.ui.city

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.goddy.citystoresdemo.R
import com.goddy.citystoresdemo.binding.FragmentDataBindingComponent
import com.goddy.citystoresdemo.databinding.FragmentCityDetailBinding
import com.goddy.citystoresdemo.di.Injectable

import com.goddy.citystoresdemo.ui.adapters.MallListAdapter
import com.goddy.citystoresdemo.ui.adapters.ShopListAdapter
import com.goddy.citystoresdemo.utils.AppExecutors
import com.goddy.citystoresdemo.utils.autoCleared
import com.goddy.citystoreslibrary.models.City
import com.goddy.citystoreslibrary.models.Shop
import kotlinx.android.synthetic.main.fragment_bottom_sheet_dialog.view.*
import kotlinx.android.synthetic.main.fragment_city_detail.*
import javax.inject.Inject

class CityDetailFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var binding by autoCleared<FragmentCityDetailBinding>()

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var adapter by autoCleared<MallListAdapter>()
    private var shopAdapter by autoCleared<ShopListAdapter>()

    private var mshopList = mutableListOf<Shop>()

    lateinit var cityDetailViewModel: CityDetailViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                com.goddy.citystoresdemo.R.layout.fragment_city_detail,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cityDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(CityDetailViewModel::class.java)
        initRecyclerView()
        val rvAdapter = MallListAdapter(
                dataBindingComponent = dataBindingComponent,
                appExecutors = appExecutors
        ) {
            mall -> run{
            val bundle = Bundle()
            bundle.putParcelable("mall",mall)
            NavHostFragment.findNavController(this@CityDetailFragment).navigate(R.id.action_cityDetailFragment_to_shopListFragment, bundle, NavOptions.Builder().setPopUpTo(R.id.cityDetailFragment, true).build())
        }
        }
        binding.mallList.adapter = rvAdapter
        adapter = rvAdapter

        backHomeLocation.setOnClickListener { View.OnClickListener {
            Navigation.findNavController(activity!!,R.id.container).navigateUp()
        } }
    }

    override fun onResume() {
        super.onResume()
        val city = arguments?.getParcelable<City>("city")
        cityName.text = city!!.name

        cityDetailViewModel.loadMallData(city.id).observe(this, Observer {
            adapter.submitList(it)
        })
    }
    private fun initRecyclerView() {
        mallList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            }
        })
    }



}
