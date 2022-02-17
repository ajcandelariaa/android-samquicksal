package com.altwav.samquicksal2.notificationsActivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ListOfOffenseAdapter
import com.altwav.samquicksal2.Adapters.OrderingBillAdapter
import com.altwav.samquicksal2.LoadingDialog
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.viewmodel.NotifBlockedViewModel
import kotlinx.android.synthetic.main.activity_blocked_notification.*
import kotlinx.android.synthetic.main.activity_runaway_notification.*

class BlockedNotificationActivity : AppCompatActivity() {
    private lateinit var viewModel: NotifBlockedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListOfOffenseAdapter
    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blocked_notification)

        clBlockedNotification.visibility = View.GONE
        loading.startLoading()


        btn_blocked_notification_back.setOnClickListener {
            finish()
        }

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val cust_id = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val notif_id = intent.getIntExtra("notificationId", 0)

        recyclerView = findViewById(R.id.offenseRecyclerView)
        adapter = ListOfOffenseAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(NotifBlockedViewModel::class.java)
        viewModel.getNotifBlockedObserver().observe(this, {
            clBlockedNotification.visibility = View.VISIBLE
            loading.isDismiss()
            if(it == null){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setOffenseHist(it.offenseHist)
                adapter.notifyDataSetChanged()
                tvBlockedDesc.text = it.description
                tvBlockedDesc2.text = it.description2
                tvBlockedHeader.text = "You have been Blocked from booking at ${it.restaurant}!"
            }
        })

        viewModel.getNotifBlockedInfo(cust_id, notif_id)

    }
}