package com.altwav.samquicksal2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.models.BHCancelledModel
import com.altwav.samquicksal2.viewmodel.BHCancelViewModel
import kotlinx.android.synthetic.main.activity_bd_noshow.*

class BdNoshowActivity : AppCompatActivity() {
    private lateinit var viewModel: BHCancelViewModel
    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bd_noshow)
        clNoShowBD.visibility = View.GONE
        loading.startLoading()

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val bookId = intent.getIntExtra("bookId", 0)
        val bookType = intent.getStringExtra("bookType")

        viewModel = ViewModelProvider(this).get(BHCancelViewModel::class.java)
        viewModel.getBHCancelledObserver().observe(this, Observer {
            if(it != null){
                clNoShowBD.visibility = View.VISIBLE
                loading.isDismiss()
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
                if(it.reserveDate == null){
                    llBDNSReserveDate.visibility = View.GONE
                    llBDNSReserveTime.visibility = View.GONE
                } else {
                    llBDNSReserveDate.visibility = View.VISIBLE
                    llBDNSReserveTime.visibility = View.VISIBLE
                    tvBDNSReserveDate.text = it.reserveDate
                    tvBDNSReserveTime.text = it.reserveTime
                }
            } else {
                Toast.makeText(this,"Error loading form", Toast.LENGTH_SHORT).show()
            }
        })


        val customer = BHCancelledModel(customerId, bookId, bookType)
        viewModel.getBHCancelledInfo(customer)

    }
}