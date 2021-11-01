package com.altwav.samquicksal2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.OrderFoodItemAdapter
import com.altwav.samquicksal2.models.AddFooditemModel
import com.altwav.samquicksal2.models.AddFooditemModelResponse
import com.altwav.samquicksal2.viewmodel.AddFoodItemViewModel
import com.altwav.samquicksal2.viewmodel.OrderingFoodItemViewModel
import kotlinx.android.synthetic.main.activity_ordering_food_item.*

class OrderingFoodItemActivity : AppCompatActivity(), AddFoodItemInterface {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderFoodItemAdapter

    private lateinit var viewModel2: AddFoodItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordering_food_item)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)

        val restAccId = intent.getIntExtra("restAccId", 0)
        val orderSetId = intent.getIntExtra("orderSetId", 0)
        val foodSetId = intent.getIntExtra("foodSetId", 0)
        val foodSetName = intent.getStringExtra("foodSetName")

        tvOrderingFoodItemTitle.text = foodSetName

        recyclerView = findViewById(R.id.orderingFoodItemsRecyclerView)
        adapter = OrderFoodItemAdapter(this)
        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        val viewModel = ViewModelProvider(this).get<OrderingFoodItemViewModel>()
        viewModel.getOrderFIObserver().observe(this, {
            if (it != null){
                adapter.setOrderingFoodItem(it, customerId)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel2 = ViewModelProvider(this).get<AddFoodItemViewModel>()
        viewModel2.getAddOrderingFIObserver().observe(this, Observer <AddFooditemModelResponse>{
            if (it != null){
                Toast.makeText(this, "${it.status}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "ERROR ADDING", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getOrderFIInfo(restAccId, orderSetId, foodSetId)
    }

    override fun addItem(foodItem: AddFooditemModel) {
        viewModel2.getAddOrderingFIInfo(foodItem)
    }


}