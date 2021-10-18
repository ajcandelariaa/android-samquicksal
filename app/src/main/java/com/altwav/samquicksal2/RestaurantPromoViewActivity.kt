package com.altwav.samquicksal2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ListOfMechanicsAdapter
import com.altwav.samquicksal2.Adapters.ListsOfPostsAdapter
import com.altwav.samquicksal2.models.LoginCustomerModel
import com.altwav.samquicksal2.models.LoginCustomerModelResponse
import com.altwav.samquicksal2.models.RestaurantPromoDetailModel
import com.altwav.samquicksal2.models.RestaurantPromoDetailModelResponse
import com.altwav.samquicksal2.viewmodel.LoginCustomerViewModel
import com.altwav.samquicksal2.viewmodel.RestaurantPromoDetailViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_restaurant_promo_view.*
import kotlinx.android.synthetic.main.fragment_about.view.*

class RestaurantPromoViewActivity : AppCompatActivity() {
    private lateinit var viewModel: RestaurantPromoDetailViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListOfMechanicsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_promo_view)

        btn_promo_details_back.setOnClickListener {
            finish()
        }

        val promoId = intent.getIntExtra("promoId", 0)
        val restaurantId = intent.getIntExtra("restaurantId", 0)


        recyclerView = findViewById(R.id.promoDetailsMechanicsRecyclerView)
        adapter = ListOfMechanicsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(RestaurantPromoDetailViewModel::class.java)
        viewModel.getRestaurantPromoDetailObserver().observe(this, Observer <RestaurantPromoDetailModelResponse>{
            if (it != null){
                adapter.setPromoMechanics(it.promoMechanics)
                adapter.notifyDataSetChanged()

                tvPromoDetailDescription.text = it.promoDescription
                tvPromoDetailTitle.text = it.promoTitle
                tvPromoDetailValidity.text = "Validity Period: ${it.promoStartDate} - ${it.promoEndDate}"

                Glide.with(this).load(it.promoImage).into(ivPromoDetailImage)
            }
        })


        val ids = RestaurantPromoDetailModel(promoId, restaurantId)
        viewModel.getPromoDetailInfo(ids)
    }
}