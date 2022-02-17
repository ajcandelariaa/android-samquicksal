package com.altwav.samquicksal2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.altwav.samquicksal2.viewmodel.CheckoutStatusViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_checkout_status.*

class CheckoutStatusActivity : AppCompatActivity() {

    private lateinit var viewModel: CheckoutStatusViewModel

    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_status)
        clCheckoutStatus.visibility = View.GONE
        loading.startLoading()

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)

        viewModel = ViewModelProvider(this).get<CheckoutStatusViewModel>()
        viewModel.getCheckoutStatusObserver().observe(this, {
            if (it != null){
                when (it.status){
                    "cashCheckoutValidation" -> {
                        Glide.with(this).load(R.drawable.paymentwaiter_icon_gif).into(ivCheckoutStatusImage)
                        tvCheckoutStatusTitle.text = "Cash Checkout"
                        tvCheckoutStatusHeader.text = "Please wait for us to collect your payment!"
                        tvCheckoutStatusDesc.text = "We will notify you once your Payment is completed."
                    }
                    "gcashCheckoutValidation" -> {
                        Glide.with(this).load(R.drawable.paymentvalidation_icon_gif).into(ivCheckoutStatusImage)
                        tvCheckoutStatusTitle.text = "GCash Checkout"
                        tvCheckoutStatusHeader.text = "You have Paid using GCash! Your payment is under Validation!"
                        tvCheckoutStatusDesc.text = "We will notify you once your GCash Receipt is validated."
                    }
                    "gcashInsufficientAmount" -> {
                        Glide.with(this).load(R.drawable.paymentwaiter_icon_gif).into(ivCheckoutStatusImage)
                        tvCheckoutStatusTitle.text = "GCash Checkout"
                        tvCheckoutStatusHeader.text = "Please wait for us to collect the remaining amount of money!"
                        tvCheckoutStatusDesc.text = "It seems like your gcash payment did not meet the total price of your bill"
                    }
                    else -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                clCheckoutStatus.visibility = View.VISIBLE
                loading.isDismiss()
            }
        })


        viewModel.getCheckoutStatusInfo(customerId!!)

        refreshCheckoutStatus.setOnRefreshListener{
            viewModel.getCheckoutStatusInfo(customerId)
            refreshCheckoutStatus.isRefreshing = false
        }

    }
}