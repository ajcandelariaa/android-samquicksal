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
import com.altwav.samquicksal2.models.NotifPendingModel
import com.altwav.samquicksal2.viewmodel.NotifPendingViewModel
import kotlinx.android.synthetic.main.activity_pending_notification.*

class PendingNotificationActivity : AppCompatActivity() {
    private lateinit var viewModel: NotifPendingViewModel
    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_notification)

        clPendingNotification.visibility = View.GONE
        loading.startLoading()


        btn_pending_notification_back.setOnClickListener {
            finish()
        }

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val cust_id = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val notif_id = intent.getIntExtra("notificationId", 0)

        viewModel = ViewModelProvider(this).get(NotifPendingViewModel::class.java)
        viewModel.getNotifPendingObserver().observe(this, Observer <NotifPendingModel>{
            clPendingNotification.visibility = View.VISIBLE
            loading.isDismiss()
            if(it == null){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            } else {
                tvPendingHeader.text = "You have booked at ${it.restaurant}! Please wait for an approval."
                tvPendingHeader2.text = "You have booked at ${it.restaurant} with the following details:"
                tvPNotifOrderName.text = it.orderName
                tvPNotifOrderNumPersons.text = it.numberOfPersons.toString()
                tvPNotifOrderNumTables.text = it.numberOfTables.toString()
                tvPNotifOrderHourStay.text = it.hoursOfStay.toString()
                tvPNotifOrderNumChild.text = it.numberOfChildren.toString()
                tvPNotifOrderNumPwd.text = it.numberOfPwd.toString()

                if(it.date != null){
                    llPNotifDate.visibility = View.VISIBLE
                    llPNotifTime.visibility = View.VISIBLE
                    tvPNotifDate.text = it.date
                    tvPNotifTime.text = it.time
                } else {
                    llPNotifDate.visibility = View.GONE
                    llPNotifTime.visibility = View.GONE
                }

                if(it.note == null){
                    tvPNotifOrderNotes.visibility = View.GONE
                } else {
                    tvPNotifOrderNotes.text = it.note
                }

                if(it.statusViewable == "yes"){
                    btn_pending_see_status.setOnClickListener {
                        val intent = Intent(this, LiveStatusActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    btn_pending_see_status.visibility = View.GONE
                }
            }
        })

        viewModel.getNotifPendingInfo(cust_id, notif_id)




    }
}