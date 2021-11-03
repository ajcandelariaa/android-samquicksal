package com.altwav.samquicksal2

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.altwav.samquicksal2.viewmodel.GCashStatusViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_checkout_status.*
import kotlinx.android.synthetic.main.activity_checkout_status.ivCheckoutStatusImage
import kotlinx.android.synthetic.main.activity_gcash_checkout.*

class GcashCheckoutActivity : AppCompatActivity() {

    private lateinit var viewModel: GCashStatusViewModel
    var DOWNLOAD_ID: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gcash_checkout)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)

        viewModel = ViewModelProvider(this).get<GCashStatusViewModel>()
        viewModel.getGCashStatusObserver().observe(this, {
            if (it != null){
                when (it.status){
                    "gcashCheckout" -> {
                        tvCOGCCInvalid.visibility = View.GONE
                        tvCOGCCTotalPrice.text = it.amount
                        Glide.with(this).load(it.restGCashQr).into(ivCOGCCImage)
                    }
                    "gcashIncorretFormat" -> {
                        tvCOGCCInvalid.visibility = View.VISIBLE
                        tvCOGCCTotalPrice.text = it.amount
                        Glide.with(this).load(it.restGCashQr).into(ivCOGCCImage)
                    }
                }

                val qrUrl = it.restGCashQr

                btnCOGCCDownloadQr.setOnClickListener {
                    val request = DownloadManager.Request(Uri.parse(qrUrl))
                        .setTitle("GCash Qr Code")
                        .setDescription("Downloading...")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)

                    val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    DOWNLOAD_ID = dm.enqueue(request)
                }

                val br = object:BroadcastReceiver(){
                    override fun onReceive(context: Context?, intent: Intent?) {
                        val id: Long? = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                        if(id == DOWNLOAD_ID){
                            Toast.makeText(applicationContext, "GCash QR Code Downloaded Successfully", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

            }
        })

        viewModel.getGCashStatusInfo(customerId!!)


    }
}