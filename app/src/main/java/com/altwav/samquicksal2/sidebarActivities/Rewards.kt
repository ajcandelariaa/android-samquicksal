package com.altwav.samquicksal2.sidebarActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ListsOfTransactionsAdapter
import com.altwav.samquicksal2.Adapters.StampCardsAdapter
import com.altwav.samquicksal2.R
import kotlinx.android.synthetic.main.activity_rewards.*

class Rewards : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StampCardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards)

        btn_rewards_back.setOnClickListener{
            finish()
        }

        recyclerView = findViewById(R.id.stampCardRecyclerView)
        adapter = StampCardsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}