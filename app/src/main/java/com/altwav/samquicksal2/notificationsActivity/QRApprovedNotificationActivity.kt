package com.altwav.samquicksal2.notificationsActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.OrderingActivity
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.viewmodel.NotifQrAppViewModel
import com.altwav.samquicksal2.viewmodel.QrReqAppDecViewModel
import kotlinx.android.synthetic.main.activity_qrapproved_notification.*

class QRApprovedNotificationActivity : AppCompatActivity() {
    private lateinit var viewModel: NotifQrAppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrapproved_notification)

        btn_qrapproved_notification_back.setOnClickListener {
            finish()
        }

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val cust_id = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val notif_id = intent.getIntExtra("notificationId", 0)

        viewModel = ViewModelProvider(this).get(NotifQrAppViewModel::class.java)
        viewModel.getNotifQrAppObserver().observe(this, {
            if (it != null) {
                tvNotifQrAppCustName.text = "Name: ${it.custName}"
                tvNotifQrAppCustEmail.text = "Email Address: ${it.custEAddress}"
                tvNotifQrAppCustCNum.text = "Contact Number: ${it.custENumber}"
                tvNotifQrAppTableN.text = "Table Number: ${it.tableNumber}"

                if(it.status == "clickable"){
                    btn_qrapp_notif_orderNow.visibility = View.VISIBLE
                    btn_qrapp_notif_orderNow.setOnClickListener {
                        val intent = Intent(this, OrderingActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    btn_qrapp_notif_orderNow.visibility = View.GONE
                }
            }
        })

        viewModel.getNotifQrAppInfo(cust_id, notif_id)





    }
}