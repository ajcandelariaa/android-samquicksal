package com.altwav.samquicksal2.notificationsActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.*
import com.altwav.samquicksal2.models.NotifCompletedModel
import com.altwav.samquicksal2.models.NotifDeclinedModel
import com.altwav.samquicksal2.viewmodel.NotifCompletedViewModel
import com.altwav.samquicksal2.viewmodel.NotifDeclinedViewModel
import kotlinx.android.synthetic.main.activity_checkout_notification.*
import kotlinx.android.synthetic.main.activity_declined_notification.*
import kotlinx.android.synthetic.main.activity_declined_notification.btn_declined_notification_back
import kotlinx.android.synthetic.main.activity_geofencing_notification.*

class CheckoutNotificationActivity : AppCompatActivity() {
    private lateinit var viewModel: NotifCompletedViewModel
    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_notification)
        clCheckoutNotification.visibility = View.GONE
        loading.startLoading()


        btn_checkout_notification_back.setOnClickListener {
            finish()
        }


        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val cust_id = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val notif_id = intent.getIntExtra("notificationId", 0)

        viewModel = ViewModelProvider(this).get(NotifCompletedViewModel::class.java)
        viewModel.getNotifCompletedObserver().observe(this, {
            clCheckoutNotification.visibility = View.VISIBLE
            loading.isDismiss()
            if(it == null){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            } else {
                tvCompletedHeader.text = "Thank You for Dining In at ${it.restaurant}!"

                btnCompletedNotifBill.setOnClickListener { it2 ->
                    val intent = Intent(this, BdCompleteActivity::class.java)
                    intent.putExtra("bookId", it.finalBookId)
                    intent.putExtra("bookType", it.finalBookType)
                    startActivity(intent)
                }

                if(it.countTasks == 0){
                    tvCompletedNotifStampS.visibility = View.GONE
                    btnCompletedNotifStamp.visibility = View.GONE
                    llCompletedNTasks.visibility = View.GONE
                } else {
                    tvCompletedNotifStampS.visibility = View.VISIBLE
                    btnCompletedNotifStamp.visibility = View.VISIBLE
                    llCompletedNTasks.visibility = View.VISIBLE

                    btnCompletedNotifStamp.setOnClickListener { it3 ->
                        val intent = Intent(this, StampDetailsActivity::class.java)
                        intent.putExtra("stamp_id", it.stampCardId)
                        startActivity(intent)
                    }

                    tvCompletedNotifStampS.text = "You have earned ${it.countTasks} Stamp for completing a Task"
                    var finalTask = ""
                    var count = 1
                    for (task in it.tasks!!){
                        if(count == 1){
                            finalTask += "$count. $task"
                        } else {
                            finalTask += "\n$count. $task"
                        }
                        count+=1
                    }
                    tvCompletedNotifTasks.text = finalTask
                }


            }
        })

        viewModel.getNotifCompletedInfo(cust_id, notif_id)
    }
}