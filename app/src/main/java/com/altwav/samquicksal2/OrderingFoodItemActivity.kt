package com.altwav.samquicksal2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ChooseOrderSetAdapter
import com.altwav.samquicksal2.Adapters.OrderFoodItemAdapter

class OrderingFoodItemActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderFoodItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordering_food_item)

        recyclerView = findViewById(R.id.orderingFoodItemsRecyclerView)
        adapter = OrderFoodItemAdapter()
        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}