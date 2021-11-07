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
import com.altwav.samquicksal2.viewmodel.NotifDeclinedViewModel
import kotlinx.android.synthetic.main.activity_approved_notification_activity.*
import kotlinx.android.synthetic.main.activity_cancelled_notification.*
import kotlinx.android.synthetic.main.activity_declined_notification.*

class DeclinedNotificationActivity : AppCompatActivity() {
    private lateinit var viewModel: NotifDeclinedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_declined_notification)

        btn_declined_notification_back.setOnClickListener {
            finish()
        }


        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val cust_id = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val notif_id = intent.getIntExtra("notificationId", 0)

        viewModel = ViewModelProvider(this).get(NotifDeclinedViewModel::class.java)
        viewModel.getNotifDeclinedObserver().observe(this, Observer <NotifDeclinedModel>{
            if(it == null){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            } else {
                tvDeclinedHeader.text = "Your Booking with ${it.restaurant} has been Declined!"
                if (it.declinedReason == null){
                    tvDNotifOrderNotes.visibility = View.GONE
                } else {
                    tvDNotifOrderNotes.text = it.declinedReason
                }
            }
        })

        viewModel.getNotifDeclinedInfo(cust_id, notif_id)
    }
}