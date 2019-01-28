package com.goddy.citystoresdemo.ui.city

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
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.goddy.citystoresdemo.binding.FragmentDataBindingComponent
import com.goddy.citystoresdemo.databinding.FragmentCityListBinding
import com.goddy.citystoresdemo.di.Injectable
import com.goddy.citystoresdemo.ui.adapters.CityListAdapter
import com.goddy.citystoresdemo.utils.AppExecutors
import com.goddy.citystoresdemo.utils.autoCleared
import javax.inject.Inject

class CityListFragment : Fragment(), Injectable{

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var binding by autoCleared<FragmentCityListBinding>()
    private var adapter by autoCleared<CityListAdapter>()

    lateinit var cityListViewModel: CityListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                com.goddy.citystoresdemo.R.layout.fragment_city_list,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cityListViewModel = ViewModelProviders.of(this, viewModelFactory).get(CityListViewModel::class.java)
        initRecyclerView()
        val rvAdapter = CityListAdapter(
                dataBindingComponent = dataBindingComponent,
                appExecutors = appExecutors
        ) {
            city -> run{
                val bundle = Bundle()
                bundle.putParcelable("city",city)
                NavHostFragment.findNavController(this@CityListFragment).navigate(com.goddy.citystoresdemo.R.id.action_cityListFragment_to_cityDetailFragment, bundle, NavOptions.Builder().setPopUpTo(com.goddy.citystoresdemo.R.id.cityListFragment, true).build())
            }
        }
        binding.cityList.adapter = rvAdapter
        adapter = rvAdapter

        cityListViewModel.cityLive.observe(this, Observer {
            updateCityList()
        })

        cityListViewModel.showErrorToast.observe(this, Observer {

            Toast.makeText(activity,"Remote Data not available ! ",Toast.LENGTH_LONG).show()
            updateCityList()
        })
    }
    private fun initRecyclerView() {
        binding.cityList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            }
        })
    }

    private fun updateCityList(){
        cityListViewModel.loadData().observe(this, Observer {
            adapter.submitList(it)
        })
    }
}

