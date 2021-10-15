package com.altwav.samquicksal2.sidebarActivities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.AccountCustomerModelResponse
import com.altwav.samquicksal2.models.HomepageCustomerModelResponse
import com.altwav.samquicksal2.viewmodel.AccountCustomerViewModel
import com.altwav.samquicksal2.viewmodel.HomepageCustomerViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_nav_drawer.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Account : AppCompatActivity() {
    private lateinit var viewModel: AccountCustomerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val customerId = intent.getIntExtra("id", 0)

        btn_account_back.setOnClickListener {
            finish()
        }

        viewModel = ViewModelProvider(this).get(AccountCustomerViewModel::class.java)
        viewModel.getAccountCustomerObserver().observe(this, Observer <AccountCustomerModelResponse>{
            if(it != null){
                tvAccountName.text = it.name
                tvHeaderAccountName.text = it.name
                tvAccountEmailAddress.text = it.emailAddress
                tvAccountContactNumber.text = it.contactNumber

                if(it.emailAddressVerified == "No"){
                    tvEmailAddressStatus.text = "Not Verified"
                    tvEmailAddressStatus.setTextColor(Color.parseColor("#91001B"))
                    Glide.with(this).load(R.drawable.ic_not_verified).into(ivEmailAddressStatus)
                } else {
                    tvEmailAddressStatus.text = "Verified"
                    tvEmailAddressStatus.setTextColor(Color.parseColor("#0AA034"))
                    Glide.with(this).load(R.drawable.ic_verified).into(ivEmailAddressStatus)
                }

                if(it.contactNumberVerified == "No"){
                    tvContactNumberStatus.text = "Not Verified"
                    tvContactNumberStatus.setTextColor(Color.parseColor("#91001B"))
                    Glide.with(this).load(R.drawable.ic_not_verified).into(ivContactNumberStatus)
                } else {
                    tvContactNumberStatus.text = "Verified"
                    tvContactNumberStatus.setTextColor(Color.parseColor("#0AA034"))
                    Glide.with(this).load(R.drawable.ic_verified).into(ivContactNumberStatus)
                }
                Glide.with(this).load(it.profileImage).into(ivAccountImageInner)
                Glide.with(this).load(it.profileImage).into(ivAccountImageOuter)
            }
        })

        if (customerId != 0){
            viewModel.getAccountInfoCustomer(customerId)
        }



    }
}