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
import kotlinx.android.synthetic.main.activity_bd_declined.*

class BdDeclinedActivity : AppCompatActivity() {
    private lateinit var viewModel: BHCancelViewModel
    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bd_declined)
        clDeclineBD.visibility = View.GONE
        loading.startLoading()

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val bookId = intent.getIntExtra("bookId", 0)
        val bookType = intent.getStringExtra("bookType")

        viewModel = ViewModelProvider(this).get(BHCancelViewModel::class.java)
        viewModel.getBHCancelledObserver().observe(this, Observer {
            if(it != null){
                clDeclineBD.visibility = View.VISIBLE
                loading.isDismiss()
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
                if(it.reserveDate == null){
                    llBDDReserveDate.visibility = View.GONE
                    llBDDReserveTime.visibility = View.GONE
                } else {
                    llBDDReserveDate.visibility = View.VISIBLE
                    llBDDReserveTime.visibility = View.VISIBLE
                    tvBDDReserveDate.text = it.reserveDate
                    tvBDDReserveTime.text = it.reserveTime
                }
            } else {
                Toast.makeText(this,"Error loading form", Toast.LENGTH_SHORT).show()
            }
        })

        val customer = BHCancelledModel(customerId, bookId, bookType)
        viewModel.getBHCancelledInfo(customer)
    }
}