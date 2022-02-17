package com.altwav.samquicksal2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ListOfMechanicsAdapter
import com.altwav.samquicksal2.models.RestaurantPromoDetailModel
import com.altwav.samquicksal2.models.RestaurantPromoDetailModelResponse
import com.altwav.samquicksal2.viewmodel.RestaurantPromoDetailViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_restaurant_promo_view.*

class RestaurantPromoViewActivity : AppCompatActivity() {
    private lateinit var viewModel: RestaurantPromoDetailViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListOfMechanicsAdapter
    private val loading = LoadingDialog(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_promo_view)
        clPromoDetails.visibility = View.GONE
        loading.startLoading()


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
                clPromoDetails.visibility = View.VISIBLE
                loading.isDismiss()
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