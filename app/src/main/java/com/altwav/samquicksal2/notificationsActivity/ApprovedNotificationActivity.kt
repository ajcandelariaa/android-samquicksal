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
import com.altwav.samquicksal2.models.SubmitQueueFormModelResponse
import com.altwav.samquicksal2.viewmodel.NotifApprovedViewModel
import com.altwav.samquicksal2.viewmodel.SubmitQueueFormViewModel
import kotlinx.android.synthetic.main.activity_approved_notification_activity.*

class ApprovedNotificationActivity : AppCompatActivity() {
    private lateinit var viewModel: NotifApprovedViewModel
    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approved_notification_activity)
        clNotificationApproved.visibility = View.GONE
        loading.startLoading()

        btn_approved_notification_back.setOnClickListener {
            finish()
        }


        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val cust_id = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val notif_id = intent.getIntExtra("notificationId", 0)

        viewModel = ViewModelProvider(this).get(NotifApprovedViewModel::class.java)
        viewModel.getNotifApprovedObserver().observe(this, Observer <NotifApprovedModel>{
            clNotificationApproved.visibility = View.VISIBLE
            loading.isDismiss()
            if(it == null){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            } else {
                tvApprovedHeader.text = "Your Booking with ${it.restaurant} has been Approved!"
                if(it.statusViewable == "yes"){
                    btn_approved_see_status.setOnClickListener {
                        val intent = Intent(this, LiveStatusActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    btn_approved_see_status.visibility = View.GONE
                }
            }
        })

        viewModel.getNotifApprovedInfo(cust_id, notif_id)


    }
}