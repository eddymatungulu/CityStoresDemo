package com.goddy.citystoresdemo.ui.shop


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.goddy.citystoresdemo.R
import kotlinx.android.synthetic.main.fragment_shop_detail.*


class ShopDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop_detail, container, false)
    }

    override fun onResume() {
        super.onResume()
        val shop = arguments?.getString("shop")
        shopName.text = shop


        backHome.setOnClickListener { View.OnClickListener {
            Navigation.findNavController(activity!!,R.id.container).navigateUp()
        } }

    }
}
