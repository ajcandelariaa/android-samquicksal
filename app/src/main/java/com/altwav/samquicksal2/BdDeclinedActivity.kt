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
import kotlinx.android.synthetic.main.activity_bd_cancelled.tvBDCBookingType
import kotlinx.android.synthetic.main.activity_bd_cancelled.tvBDCDate
import kotlinx.android.synthetic.main.activity_bd_cancelled.tvBDCHoursOfStay
import kotlinx.android.synthetic.main.activity_bd_cancelled.tvBDCNotes
import kotlinx.android.synthetic.main.activity_bd_cancelled.tvBDCNumberOfChildren
import kotlinx.android.synthetic.main.activity_bd_cancelled.tvBDCNumberOfPwd
import kotlinx.android.synthetic.main.activity_bd_cancelled.tvBDCOrderName
import kotlinx.android.synthetic.main.activity_bd_cancelled.tvBDCReason
import kotlinx.android.synthetic.main.activity_bd_cancelled.tvBDCRestaurantAddress
import kotlinx.android.synthetic.main.activity_bd_cancelled.tvBDCRestaurantName
import kotlinx.android.synthetic.main.activity_bd_cancelled.tvBDCReward
import kotlinx.android.synthetic.main.activity_bd_cancelled.tvBDCTime
import kotlinx.android.synthetic.main.activity_bd_declined.*

class BdDeclinedActivity : AppCompatActivity() {
    private lateinit var viewModel: BHCancelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bd_declined)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val bookId = intent.getIntExtra("bookId", 0)
        val bookType = intent.getStringExtra("bookType")

        viewModel = ViewModelProvider(this).get(BHCancelViewModel::class.java)
        viewModel.getBHCancelledObserver().observe(this, Observer {
            if(it != null){
                tvBDDDate.text = it.bookDate
                tvBDDTime.text = it.bookTime
                tvBDDRestaurantName.text = it.rName
                tvBDDRestaurantAddress.text = it.rAddress
                tvBDDOrderName.text = it.orderName
                tvBDDHoursOfStay.text = it.hoursOfStay
                tvBDDNumberOfPwd.text = it.numberOfPwd.toString()
                tvBDDNumberOfChildren.text = it.numberOfChildren.toString()
                tvBDDReward.text = it.reward
                tvBDDRewardClaimed.text = it.rewardClaimed
                tvBDDBookingType.text = it.bookingType
                tvBDDNotes.text = it.notes
                tvBDDReason.text = it.reason
            } else {
                Toast.makeText(this,"Error loading form", Toast.LENGTH_SHORT).show()
            }
        })

        val customer = BHCancelledModel(customerId, bookId, bookType)
        viewModel.getBHCancelledInfo(customer)
    }
}