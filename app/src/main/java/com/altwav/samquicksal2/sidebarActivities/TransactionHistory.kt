package com.altwav.samquicksal2.sidebarActivities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ListsOfTransactionsAdapter
import com.altwav.samquicksal2.LoadingDialog
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.BookingHistoryModelResponse
import com.altwav.samquicksal2.viewmodel.BookingHistoryViewModel
import kotlinx.android.synthetic.main.activity_transaction_history.*

class TransactionHistory : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListsOfTransactionsAdapter
    private lateinit var viewModel: BookingHistoryViewModel
    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)
        clBookingHistories.visibility = View.GONE
        loading.startLoading()

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)

        btn_transaction_back.setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.transactionsRecyclerView)
        adapter = ListsOfTransactionsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(BookingHistoryViewModel::class.java)
        viewModel.getBookingHistoryObserver().observe(this, Observer <List<BookingHistoryModelResponse>>{
            if (it == null || it.isEmpty()){
                clBookingHistories.visibility = View.VISIBLE
                loading.isDismiss()
                containerNoBookingHistory.visibility = ViewGroup.VISIBLE
            } else {
                containerNoBookingHistory.visibility = ViewGroup.GONE
                clBookingHistories.visibility = View.VISIBLE
                loading.isDismiss()
                adapter.setBookHistory(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.getBookingHistory(customerId)
    }
}