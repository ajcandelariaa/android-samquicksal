package com.altwav.samquicksal2

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.models.CancelBookingModelResponse
import com.altwav.samquicksal2.models.LiveStatusModelResponse
import com.altwav.samquicksal2.models.RestaurantPromoDetailModel
import com.altwav.samquicksal2.models.RestaurantPromoDetailModelResponse
import com.altwav.samquicksal2.viewmodel.CancelBookingViewModel
import com.altwav.samquicksal2.viewmodel.LiveStatusViewModel
import com.altwav.samquicksal2.viewmodel.RestaurantPromoDetailViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_live_status.*
import kotlinx.android.synthetic.main.activity_restaurant_promo_view.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*

class LiveStatusActivity : AppCompatActivity() {
    private lateinit var viewModel: LiveStatusViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_status)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)

        val actType = intent.getStringExtra("actType")

        Log.d("message", "$actType")

        Glide.with(this).load(R.drawable.live_status_circle).into(ivLiveStatusGif)

        btn_live_status_back.setOnClickListener {
            if (actType != null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                finish()
            }
        }

        btn_live_status_back2.setOnClickListener {
            if (actType != null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                finish()
            }
        }

        viewModel = ViewModelProvider(this).get(LiveStatusViewModel::class.java)
        viewModel.getLiveStatusObserver().observe(this, Observer <LiveStatusModelResponse>{
            if (it != null){
                when(it.liveStatusHeader){
                    // PENDING STATUS
                    "Cancellation Time" -> {
                        tvLiveStatusBody.visibility = View.GONE
                        tvLiveStatusHeader.text = it.liveStatusHeader
                        tvLiveStatusNumber.text = it.liveStatusNumber.toString()
                        tvLiveStatusNumberDesc.text = it.liveStatusNumberDesc
                        tvLiveStatusDescription.text = it.liveStatusDescription
                        if (it.liveStatusNumber == 0 && it.liveStatusNumberDesc == "second"){
                            btn_live_status_cancel_booking.visibility = View.GONE
                        } else {
                            val viewModel2: CancelBookingViewModel = ViewModelProvider(this).get(CancelBookingViewModel::class.java)
                            viewModel2.getCancelBookObserver().observe(this, Observer <CancelBookingModelResponse>{ it2 ->
                                if(it2.status != null){
                                    Toast.makeText(this,"Cancelled Queue Successfully", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this,"$it2.status", Toast.LENGTH_SHORT).show()
                                }
                            })
                            btn_live_status_cancel_booking.setOnClickListener {
                                AlertDialog.Builder(this)
                                    .setTitle("Cancel Queue")
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setMessage("Are you sure you want to Cancel your Queue?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes") { dialog, id ->
                                        viewModel2.getCancelBookInfo(customerId)
                                    }
                                    .setNegativeButton("No") { dialog, id ->
                                        dialog.cancel()
                                    }
                                    .show()
                            }
                        }
                    }
                    // APPROVED STATUS
                    "Queue Number" -> {
                        tvLiveStatusBody.visibility = View.GONE
                        tvLiveStatusHeader.text = it.liveStatusHeader
                        tvLiveStatusNumber.text = it.liveStatusNumber.toString()
                        tvLiveStatusNumberDesc.text = it.liveStatusNumberDesc
                        tvLiveStatusDescription.text = it.liveStatusDescription
                        if (it.liveStatusBody == "no"){
                            btn_live_status_cancel_booking.visibility = View.GONE
                        } else {
                            btn_live_status_cancel_booking.text = "Unqueue"
                            val viewModel2: CancelBookingViewModel = ViewModelProvider(this).get(CancelBookingViewModel::class.java)
                            viewModel2.getCancelBookObserver().observe(this, Observer <CancelBookingModelResponse>{ it2 ->
                                if(it2.status != null){
                                    Toast.makeText(this,"Cancelled Queue Successfully", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this,"$it2.status", Toast.LENGTH_SHORT).show()
                                }
                            })
                            btn_live_status_cancel_booking.setOnClickListener {
                                AlertDialog.Builder(this)
                                    .setTitle("Unqueue")
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setMessage("Are you sure you want to Unqueue?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes") { dialog, id ->
                                        viewModel2.getCancelBookInfo(customerId)
                                    }
                                    .setNegativeButton("No") { dialog, id ->
                                        dialog.cancel()
                                    }
                                    .show()
                            }
                        }
                    }
                    "Confirmation Time" -> {
                        tvLiveStatusBody.visibility = View.GONE
                        btn_live_status_cancel_booking.visibility = View.GONE

                        tvLiveStatusHeader.text = it.liveStatusHeader
                        tvLiveStatusNumber.text = it.liveStatusNumber.toString()
                        tvLiveStatusNumberDesc.text = it.liveStatusNumberDesc
                        tvLiveStatusDescription.text = it.liveStatusDescription
                    }
                    "Note" -> {
                        tvLiveStatusNumber.visibility = View.GONE
                        tvLiveStatusNumberDesc.visibility = View.GONE
                        btn_live_status_cancel_booking.visibility = View.GONE

                        tvLiveStatusBody.visibility = View.VISIBLE
                        tvLiveStatusBody.text = it.liveStatusBody
                        tvLiveStatusHeader.text = it.liveStatusHeader
                        tvLiveStatusDescription.text = it.liveStatusDescription
                    }
                    else -> {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        })

        viewModel.getLiveStatusInfo(customerId)


        refreshLiveStatus.setOnRefreshListener {
            viewModel.getLiveStatusInfo(customerId)
            refreshLiveStatus.isRefreshing = false
        }

    }

    override fun onBackPressed() {
        val actType = intent.getStringExtra("actType")
        if (actType != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            finish()
        }
    }
}