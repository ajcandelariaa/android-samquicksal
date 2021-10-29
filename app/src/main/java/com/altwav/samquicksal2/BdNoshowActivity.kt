package com.altwav.samquicksal2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.models.BHCancelledModel
import com.altwav.samquicksal2.viewmodel.BHCancelViewModel
import kotlinx.android.synthetic.main.activity_bd_cancelled.*
import kotlinx.android.synthetic.main.activity_bd_declined.*
import kotlinx.android.synthetic.main.activity_bd_noshow.*

class BdNoshowActivity : AppCompatActivity() {
    private lateinit var viewModel: BHCancelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bd_noshow)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val bookId = intent.getIntExtra("bookId", 0)
        val bookType = intent.getStringExtra("bookType")

        viewModel = ViewModelProvider(this).get(BHCancelViewModel::class.java)
        viewModel.getBHCancelledObserver().observe(this, Observer {
            if(it != null){
                tvBDNSDate.text = it.bookDate
                tvBDNSTime.text = it.bookTime
                tvBDNSRestaurantName.text = it.rName
                tvBDNSRestaurantAddress.text = it.rAddress
                tvBDNSOrderName.text = it.orderName
                tvBDNSHoursOfStay.text = it.hoursOfStay
                tvBDNSNumberOfPwd.text = it.numberOfPwd.toString()
                tvBDNSNumberOfChildren.text = it.numberOfChildren.toString()
                tvBDNSReward.text = it.reward
                tvBDNSRewardClaimed.text = it.rewardClaimed
                tvBDNSBookingType.text = it.bookingType
                tvBDNSNotes.text = it.notes
            } else {
                Toast.makeText(this,"Error loading form", Toast.LENGTH_SHORT).show()
            }
        })


        val customer = BHCancelledModel(customerId, bookId, bookType)
        viewModel.getBHCancelledInfo(customer)

    }
}