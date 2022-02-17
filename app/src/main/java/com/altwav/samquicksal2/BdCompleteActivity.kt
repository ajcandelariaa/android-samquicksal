package com.altwav.samquicksal2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.OrderingBillAdapter
import com.altwav.samquicksal2.models.BHCompleteModel
import com.altwav.samquicksal2.viewmodel.BHCompleteViewModel
import kotlinx.android.synthetic.main.activity_bd_complete.*

class BdCompleteActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderingBillAdapter

    private lateinit var viewModel: BHCompleteViewModel
    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bd_complete)
        clCompleteBD.visibility = View.GONE
        loading.startLoading()

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val bookId = intent.getIntExtra("bookId", 0)
        val bookType = intent.getStringExtra("bookType")


        recyclerView = findViewById(R.id.bdCompleteRecyclerView)
        adapter = OrderingBillAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter


        viewModel = ViewModelProvider(this).get(BHCompleteViewModel::class.java)
        viewModel.getBHCompleteObserver().observe(this, Observer {
            if(it != null){
                clCompleteBD.visibility = View.VISIBLE
                loading.isDismiss()
                tvBDCompDate.text = it.bookDate
                tvBDCompCheckIn.text = it.checkIn
                tvBDCompCheckOut.text = it.checkOut
                tvBDCompRestaurantName.text = it.rName
                tvBDCompRestaurantAddress.text = it.rAddress
                tvBDCompTableNumber.text = it.tableNumber
                tvBDCompBookingType.text = it.bookingType
                adapter.setOrderingbill(it.orders)
                adapter.notifyDataSetChanged()
                tvBDCompOrderSet.text = it.orderSetName
                tvBDCompOrderSetQuantity.text = "${it.numberOfPersons.toString()}x"
                tvBDCompOrderSetTPrice.text = it.orderSetPriceTotal
                tvBDCompSubtotal.text = it.subtotal
                tvBDCompNumberPwd.text = "${it.numberOfPwd.toString()}x"
                tvBDCompPwdTDiscount.text = it.pwdDiscount
                tvBDCompChildrenPercent.text = "Children (${it.childrenPercentage.toString()}%)"
                tvBDCompNumberChildren.text = "${it.numberOfChildren.toString()}x"
                tvBDCompChildrenTDiscount.text = it.childrenDiscount
                tvBDCompPromoDiscount.text = it.promoDiscount
                tvBDCompAdditionalDiscount.text = it.additionalDiscount
                tvBDCompReward.text = "Reward (${it.rewardName})"
                tvBDCompRewardDiscount.text = it.rewardDiscount
                tvBDCompOffenseCharge.text = it.offenseCharge
                tvBDCompTotalPrice.text = it.totalPrice
            } else {
                Toast.makeText(this,"Error loading form", Toast.LENGTH_SHORT).show()
            }
        })


        val customer = BHCompleteModel(customerId, bookId, bookType)
        viewModel.getBHCompleteInfo(customer)
    }
}