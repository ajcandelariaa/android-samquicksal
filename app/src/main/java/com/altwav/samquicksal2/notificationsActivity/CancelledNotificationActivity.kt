package com.altwav.samquicksal2.notificationsActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.LiveStatusActivity
import com.altwav.samquicksal2.LoadingDialog
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.NotifApprovedModel
import com.altwav.samquicksal2.models.NotifCancelledModel
import com.altwav.samquicksal2.models.NotifPendingModel
import com.altwav.samquicksal2.viewmodel.NotifApprovedViewModel
import com.altwav.samquicksal2.viewmodel.NotifCancelledViewModel
import kotlinx.android.synthetic.main.activity_approved_notification_activity.*
import kotlinx.android.synthetic.main.activity_approved_notification_activity.btn_approved_notification_back
import kotlinx.android.synthetic.main.activity_cancelled_notification.*

class CancelledNotificationActivity : AppCompatActivity() {
    private lateinit var viewModel: NotifCancelledViewModel
    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancelled_notification)

        clCancelledNotification.visibility = View.GONE
        loading.startLoading()


        btn_cancelled_notification_back.setOnClickListener {
            finish()
        }


        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val cust_id = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val notif_id = intent.getIntExtra("notificationId", 0)

        viewModel = ViewModelProvider(this).get(NotifCancelledViewModel::class.java)
        viewModel.getNotifCancelledObserver().observe(this, Observer <NotifCancelledModel>{
            clCancelledNotification.visibility = View.VISIBLE
            loading.isDismiss()
            if(it == null){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            } else {
                tvCancelledHeader.text = "Your Booking with ${it.restaurant} has been Cancelled!"
                if (it.cancelReason == null){
                    tvCNotifOrderNotes.visibility = View.GONE
                } else {
                    tvCNotifOrderNotes.text = it.cancelReason
                }
            }
        })

        viewModel.getNotifCancelledInfo(cust_id, notif_id)
    }
}