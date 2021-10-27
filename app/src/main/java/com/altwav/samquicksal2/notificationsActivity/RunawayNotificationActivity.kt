package com.altwav.samquicksal2.notificationsActivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.NotifNoShowModel
import com.altwav.samquicksal2.models.NotifRunawayModel
import com.altwav.samquicksal2.viewmodel.NotifDeclinedViewModel
import com.altwav.samquicksal2.viewmodel.NotifNoShowViewModel
import com.altwav.samquicksal2.viewmodel.NotifRunawayViewModel
import kotlinx.android.synthetic.main.activity_no_show_notification.*
import kotlinx.android.synthetic.main.activity_runaway_notification.*

class RunawayNotificationActivity : AppCompatActivity() {
    private lateinit var viewModel: NotifRunawayViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_runaway_notification)

        btn_runaway_notification_back.setOnClickListener {
            finish()
        }

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val cust_id = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val notif_id = intent.getIntExtra("notificationId", 0)

        viewModel = ViewModelProvider(this).get(NotifRunawayViewModel::class.java)
        viewModel.getNotifRunawayObserver().observe(this, Observer <NotifRunawayModel>{
            if(it == null){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            } else {
                tvRunawayHeader.text = "Your Booking with ${it.restaurant} has not been paid yet!"
            }
        })

        viewModel.getNotifRunawayInfo(cust_id, notif_id.toInt())
    }
}