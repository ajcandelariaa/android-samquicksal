package com.altwav.samquicksal2.notificationsActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ChooseOrderSetAdapter
import com.altwav.samquicksal2.Adapters.GeofencingMenuAdapter
import com.altwav.samquicksal2.Adapters.GeofencingPromosAdapter
import com.altwav.samquicksal2.LiveStatusActivity
import com.altwav.samquicksal2.LoadingDialog
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.RestaurantViewActivity
import com.altwav.samquicksal2.models.NotifPendingModel
import com.altwav.samquicksal2.viewmodel.NotifGeofencingViewModel
import com.altwav.samquicksal2.viewmodel.NotifPendingViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_geofencing_notification.*
import kotlinx.android.synthetic.main.activity_pending_notification.*
import kotlinx.android.synthetic.main.fragment_about.view.*

class GeofencingNotificationActivity : AppCompatActivity() {
    private lateinit var viewModel: NotifGeofencingViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GeofencingPromosAdapter

    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter2: GeofencingMenuAdapter

    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geofencing_notification)

        clGeofencingNotification.visibility = View.GONE
        loading.startLoading()


        btn_geofencing_notification_back.setOnClickListener {
            finish()
        }

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val cust_id = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val notif_id = intent.getIntExtra("notificationId", 0)

        recyclerView = findViewById(R.id.geofencingPromoRv)
        adapter = GeofencingPromosAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        recyclerView2 = findViewById(R.id.geofencingMenuRv)
        adapter2 = GeofencingMenuAdapter()
        recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView2.adapter = adapter2

        viewModel = ViewModelProvider(this).get(NotifGeofencingViewModel::class.java)
        viewModel.getNotifGeofencingObserver().observe(this, {
            clGeofencingNotification.visibility = View.VISIBLE
            loading.isDismiss()
            if(it == null){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            } else {
                tvGeofencingHeader.text = "Hey! You are Near at ${it.rName} ${it.rBranch}!"
                Glide.with(this).load(it.rLogo).into(ivGeofencingRLogo)
                tvGeofencingRName.text = it.rName
                tvGeofencingRAddress.text = it.rAddress
                tvgeofencingAbovePromo.text = "We would like you to try out our latest promotions and what we have to offer here at ${it.rName} ${it.rBranch}! Scroll down to learn more"
                btnGeofencingBookNow.setOnClickListener{ it2 ->
                    val intent = Intent(this, RestaurantViewActivity::class.java)
                    intent.putExtra("restaurantId", it.restAcc_id)
                    intent.putExtra("restaurantName", it.rName)
                    startActivity(intent)
                }
                if(it.promos == null){
                    clGeofencingPromoContainer.visibility = View.GONE
                } else {
                    clGeofencingPromoContainer.visibility = View.VISIBLE
                    adapter.setGeoPromos(it.promos)
                    adapter.notifyDataSetChanged()
                }

                if(it.stampDiscount == null || it.stampCapacity == null || it.stampValidity == null || it.stampTasks == null){
                    clGeofencingRewardContainer.visibility = View.GONE
                } else {
                    clGeofencingRewardContainer.visibility = View.VISIBLE
                    tvGeofencingRewardName.text = it.stampDiscount
                    tvGeofencingRewardValidity.text = it.stampValidity
                    tvGeofencingRewardCapacity.text = it.stampCapacity
                    var finalTask = ""
                    var count = 1
                    for (task in it.stampTasks){
                        if(count == 1){
                            finalTask += "$count. $task"
                        } else {
                            finalTask += "\n$count. $task"
                        }
                        count+=1
                    }
                    tvGeoFencingTasks.text = finalTask
                }

                if(it.orderSets == null){
                    clGeofencingMenuContainer.visibility = View.GONE
                } else {
                    clGeofencingMenuContainer.visibility = View.VISIBLE
                    adapter2.setGeoMenu(it.orderSets)
                    adapter2.notifyDataSetChanged()

                }


            }
        })


        viewModel.getNotifGeofencingInfo(cust_id, notif_id)
    }
}