package com.goddy.citystoresdemo.ui.adapters

import android.view.View
import com.goddy.citystoresdemo.R
import com.goddy.citystoresdemo.ui.adapters.viewHolder.BaseViewHolder
import com.goddy.citystoresdemo.ui.adapters.viewHolder.CityViewHolder
import com.goddy.citystoreslibrary.models.City

class CityListAdapter(val delegate:CityViewHolder.Delegate):BaseAdapter() {

    private val section_city = 0

    init{
        addSection(ArrayList<City>())
    }

    fun addCityList(cities: List<City>) {
        sections[section_city].addAll(cities)
        notifyDataSetChanged()
    }

    fun clearAll() {
        sections[section_city].clear()
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: SectionRow): Int {
        return R.layout.city_item
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return CityViewHolder(view, delegate)
    }
}