package com.altwav.samquicksal2.notificationsActivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.NotifDeclinedModel
import com.altwav.samquicksal2.models.NotifNoShowModel
import com.altwav.samquicksal2.viewmodel.NotifDeclinedViewModel
import com.altwav.samquicksal2.viewmodel.NotifNoShowViewModel
import kotlinx.android.synthetic.main.activity_approved_notification_activity.*
import kotlinx.android.synthetic.main.activity_declined_notification.*
import kotlinx.android.synthetic.main.activity_no_show_notification.*

class NoShowNotificationActivity : AppCompatActivity() {
    private lateinit var viewModel: NotifNoShowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_show_notification)

        btn_noshow_notification_back.setOnClickListener {
            finish()
        }

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val cust_id = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val notif_id = intent.getIntExtra("notificationId", 0)

        viewModel = ViewModelProvider(this).get(NotifNoShowViewModel::class.java)
        viewModel.getNotifNoShowObserver().observe(this, Observer <NotifNoShowModel>{
            if(it == null){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            } else {
                tvNoShowHeader.text = "Your Booking with ${it.restaurant} has been labelled No Show!"
            }
        })

        viewModel.getNotifNoShowInfo(cust_id, notif_id.toInt())
    }
}