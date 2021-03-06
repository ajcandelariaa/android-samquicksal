package com.altwav.samquicksal2.sidebarActivities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.StampCardsAdapter
import com.altwav.samquicksal2.LoadingDialog
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.viewmodel.StampCardsViewModel
import kotlinx.android.synthetic.main.activity_rewards.*

class Rewards : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StampCardsAdapter

    private lateinit var viewModel: StampCardsViewModel
    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards)
        clStampCards.visibility = View.GONE
        loading.startLoading()

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)

        btn_rewards_back.setOnClickListener{
            finish()
        }

        recyclerView = findViewById(R.id.stampCardRecyclerView)
        adapter = StampCardsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(StampCardsViewModel::class.java)
        viewModel.getStampCardsObserver().observe(this,{
            if (it != null){
                containerNoStampCards.visibility = View.GONE
                adapter.setStampCards(it)
                adapter.notifyDataSetChanged()
            } else {
                containerNoStampCards.visibility = View.VISIBLE
            }
            clStampCards.visibility = View.VISIBLE
            loading.isDismiss()
        })

        viewModel.getStampCardsInfo(customerId)

    }
}