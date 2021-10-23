package com.altwav.samquicksal2

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ChooseOrderSetAdapter
import com.altwav.samquicksal2.models.ChooseOrderSetModelResponse
import com.altwav.samquicksal2.viewmodel.ChooseOrderSetViewModel
import kotlinx.android.synthetic.main.activity_choose_order_set.*

class ChooseOrderSetActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChooseOrderSetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_order_set)

        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)

        val restaurantId = intent.getIntExtra("restaurantId", 0)
        val bookType = intent.getStringExtra("bookType")

        tvChooseOrderSetTitle.text = bookType.toString()

        btn_choose_order_set_back.setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.chooseOrderSetRecyclerView)
        adapter = ChooseOrderSetAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        val viewModel = ViewModelProvider(this).get<ChooseOrderSetViewModel>()
        viewModel.getAccountCustomerObserver().observe(this, Observer <ChooseOrderSetModelResponse>{
            if(it != null){
                adapter.setChooseOrderSet(
                    it.orderSets,
                    restaurantId,
                    bookType!!,
                    it.rTimeLimit!!,
                    it.rCapacityPerTable!!,
                    it.rewardStatus!!,
                    it.rewardType!!,
                    it.rewardInput!!
                )
                adapter.notifyDataSetChanged()
                tvChooseOrderSetRestaurantName.text = "${it.restaurantName} Order Sets"
            }
        })

        viewModel.getAccountInfoCustomer(restaurantId, customerId)

    }
}