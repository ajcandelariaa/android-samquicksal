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
import kotlinx.android.synthetic.main.activity_bd_cancelled.*

class BdCancelledActivity : AppCompatActivity() {
    private lateinit var viewModel: BHCancelViewModel
    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bd_cancelled)
        clCancelBD.visibility = View.GONE
        loading.startLoading()

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val bookId = intent.getIntExtra("bookId", 0)
        val bookType = intent.getStringExtra("bookType")

        viewModel = ViewModelProvider(this).get(BHCancelViewModel::class.java)
        viewModel.getBHCancelledObserver().observe(this, Observer {
            if(it != null){
                clCancelBD.visibility = View.VISIBLE
                loading.isDismiss()
                tvBDCDate.text = it.bookDate
                tvBDCTime.text = it.bookTime
                tvBDCRestaurantName.text = it.rName
                tvBDCRestaurantAddress.text = it.rAddress
                tvBDCOrderName.text = it.orderName
                tvBDCHoursOfStay.text = it.hoursOfStay
                tvBDCNumberOfPwd.text = it.numberOfPwd.toString()
                tvBDCNumberOfChildren.text = it.numberOfChildren.toString()
                tvBDCReward.text = it.reward
                tvBDCRewardClaimed.text = it.rewardClaimed
                tvBDCBookingType.text = it.bookingType
                tvBDCNotes.text = it.notes
                tvBDCReason.text = it.reason
                if(it.reserveDate == null){
                    llBDCReserveDate.visibility = View.GONE
                    llBDCReserveTime.visibility = View.GONE
                } else {
                    llBDCReserveDate.visibility = View.VISIBLE
                    llBDCReserveTime.visibility = View.VISIBLE
                    tvBDCReserveDate.text = it.reserveDate
                    tvBDCReserveTime.text = it.reserveTime
                }
            } else {
                Toast.makeText(this,"Error loading form", Toast.LENGTH_SHORT).show()
            }
        })


        val customer = BHCancelledModel(customerId, bookId, bookType)
        viewModel.getBHCancelledInfo(customer)
    }
}