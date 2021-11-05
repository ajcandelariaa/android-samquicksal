package com.altwav.samquicksal2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.OrderingBillAdapter
import com.altwav.samquicksal2.models.BHCompleteModel
import com.altwav.samquicksal2.viewmodel.BHCompleteViewModel
import kotlinx.android.synthetic.main.activity_bd_complete.*
import kotlinx.android.synthetic.main.activity_bd_runaway.*

class BdRunawayActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderingBillAdapter

    private lateinit var viewModel: BHCompleteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bd_runaway)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val bookId = intent.getIntExtra("bookId", 0)
        val bookType = intent.getStringExtra("bookType")

        recyclerView = findViewById(R.id.bdRunRecyclerView)
        adapter = OrderingBillAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(BHCompleteViewModel::class.java)
        viewModel.getBHCompleteObserver().observe(this, Observer {
            if(it != null){
                tvBDRunDate.text = it.bookDate
                tvBDRunCheckIn.text = it.checkIn
                tvBDRunCheckOut.text = it.checkOut
                tvBDRunRestaurantName.text = it.rName
                tvBDRunRestaurantAddress.text = it.rAddress
                tvBDRunTableNumber.text = it.tableNumber
                tvBDRunBookingType.text = it.bookingType
                adapter.setOrderingbill(it.orders)
                adapter.notifyDataSetChanged()
                tvBDRunOrderSet.text = it.orderSetName
                tvBDRunOrderSetQuantity.text = "${it.numberOfPersons.toString()}x"
                tvBDRunOrderSetTPrice.text = it.orderSetPriceTotal
                tvBDRunSubtotal.text = it.subtotal
                tvBDRunNumberPwd.text = "${it.numberOfPwd.toString()}x"
                tvBDRunPwdTDiscount.text = it.pwdDiscount
                tvBDRunChildrenPercent.text = "Children (${it.childrenPercentage.toString()}%)"
                tvBDRunNumberChildren.text = "${it.numberOfChildren.toString()}x"
                tvBDRunChildrenTDiscount.text = it.childrenDiscount
                tvBDRunPromoDiscount.text = it.promoDiscount
                tvBDRunAdditionalDiscount.text = it.additionalDiscount
                tvBDRunReward.text = "Reward (${it.rewardName})"
                tvBDRunRewardDiscount.text = it.rewardDiscount
                tvBDRunOffenseCharge.text = it.offenseCharge
                tvBDRunTotalPrice.text = it.totalPrice
            } else {
                Toast.makeText(this,"Error loading form", Toast.LENGTH_SHORT).show()
            }
        })


        val customer = BHCompleteModel(customerId, bookId, bookType)
        viewModel.getBHCompleteInfo(customer)

    }
}