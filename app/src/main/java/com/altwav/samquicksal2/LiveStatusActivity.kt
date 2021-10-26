package com.altwav.samquicksal2

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class LiveStatusActivity : AppCompatActivity() {
    private lateinit var viewModel: LiveStatusViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_status)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)

        Glide.with(this).load(R.drawable.live_status_circle).into(ivLiveStatusGif)
        btn_live_status_back.setOnClickListener {
            finish()
        }

        btn_live_status_back2.setOnClickListener {
            finish()
        }

        viewModel = ViewModelProvider(this).get(LiveStatusViewModel::class.java)
        viewModel.getLiveStatusObserver().observe(this, Observer <LiveStatusModelResponse>{
            if (it != null){
                when(it.liveStatusHeader){
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
                    "Queue Number" -> {
                        tvLiveStatusBody.visibility = View.GONE
                        tvLiveStatusHeader.text = it.liveStatusHeader
                        tvLiveStatusNumber.text = it.liveStatusNumber.toString()
                        tvLiveStatusNumberDesc.text = it.liveStatusNumberDesc
                        tvLiveStatusDescription.text = it.liveStatusDescription
                        if (it.liveStatusBody == "no"){
                            btn_live_status_cancel_booking.visibility = View.GONE
                        } else {
                            val viewModel2: CancelBookingViewModel = ViewModelProvider(this).get(CancelBookingViewModel::class.java)
                            viewModel2.getCancelBookObserver().observe(this, Observer <CancelBookingModelResponse>{ it2 ->
                                if(it2.status != null){
                                    Toast.makeText(this,"Cancelled Queue Successfully", Toast.LENGTH_SHORT).show()
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

                    }
                    "Note" -> {

                    }
                }
            }
        })

        viewModel.getLiveStatusInfo(customerId)

    }
}