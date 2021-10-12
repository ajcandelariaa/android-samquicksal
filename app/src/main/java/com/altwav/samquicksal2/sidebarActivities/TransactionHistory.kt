package com.altwav.samquicksal2.sidebarActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ListsOfParticularPromosAdapter
import com.altwav.samquicksal2.Adapters.ListsOfTransactionsAdapter
import com.altwav.samquicksal2.R
import kotlinx.android.synthetic.main.activity_transaction_history.*

class TransactionHistory : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListsOfTransactionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)
        supportActionBar?.hide()

        btn_transaction_back.setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.transactionsRecyclerView)
        adapter = ListsOfTransactionsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter


    }
}