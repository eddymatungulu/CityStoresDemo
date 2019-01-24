package com.goddy.citystoresdemo.ui.city


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goddy.citystoresdemo.R
import com.goddy.citystoresdemo.binding.FragmentDataBindingComponent
import com.goddy.citystoresdemo.databinding.FragmentCityListBinding
import com.goddy.citystoresdemo.di.Injectable
import com.goddy.citystoresdemo.ui.adapters.viewHolder.CityViewHolder
import com.goddy.citystoresdemo.ui.widget.RecyclerViewPaginator
import com.goddy.citystoresdemo.utils.autoCleared
import com.goddy.citystoreslibrary.models.City
import kotlinx.android.synthetic.main.fragment_city_list.*
import javax.inject.Inject

class CityListFragment : Fragment(), Injectable , CityViewHolder.Delegate{


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var binding by autoCleared<FragmentCityListBinding>()
    private val adapter by lazy { CityListAdapter(this) }

    private lateinit var paginator:RecyclerViewPaginator

    lateinit var cityListViewModel: CityListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_city_list,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cityListViewModel = ViewModelProviders.of(this, viewModelFactory).get(CityListViewModel::class.java)

        cityList.adapter = adapter
        cityList.layoutManager = LinearLayoutManager(activity)
        paginator = RecyclerViewPaginator(
                recyclerView = cityList,
                isLoading = {cityListViewModel.fetchStatus.isOnLoading},
                loadMore = {loadMore(it)},
                onLast = { cityListViewModel.fetchStatus.isOnLast}        )



    }

    private fun updateCityList(){
        cityListViewModel.loadData().observe(this, Observer {

        })

    }

    override fun onItemClick(city: City) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun loadMore(page: Int) {
        //cityListViewModel.postPage(page)
    }

}
