package com.altwav.samquicksal2

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.altwav.samquicksal2.models.OrderingAssistanceModel
import com.altwav.samquicksal2.viewmodel.GCashStatusViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_gcash_checkout.*

class GcashCheckoutActivity : AppCompatActivity() {

    private lateinit var viewModel: GCashStatusViewModel
    var mydownloadid: Long = 0

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

//                // DOWNLOAD IMAGE
                val qrUrl = it.restGCashQr
                val fileName = it.filename
                btnCOGCCDownloadQr.setOnClickListener {
                    val request = DownloadManager.Request(Uri.parse(qrUrl))
                        .setTitle("gc-qr-$fileName")
                        .setDescription("Downloading...")
                        .setAllowedOverMetered(true)
                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)


                    val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    mydownloadid = dm.enqueue(request)
                }
                val br = object: BroadcastReceiver(){
                    override fun onReceive(context: Context?, intent: Intent?) {
                        val id: Long? = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                        if(id == mydownloadid){
                            Toast.makeText(applicationContext, "Downloaded Successfully", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

                btnCOGCCUploadImage.setOnClickListener {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivity(intent)
                }

                btnCOGCCSubmitReceipt.setOnClickListener {
                    if(tvCOGCCUploadImageLabel.text == "Upload Receipt Here"){
                        Toast.makeText(applicationContext, "Please choose an image first", Toast.LENGTH_SHORT).show()
                    } else {
                        AlertDialog.Builder(this)
                            .setTitle("Upload Receipt")
                            .setIcon(R.mipmap.ic_launcher)
                            .setMessage("Are you sure you want upload your receipt?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->

                            }
                            .setNegativeButton("No") { dialog, id ->
                                dialog.cancel()
                            }
                            .show()
                    }
                }

            }
        })

        viewModel.getGCashStatusInfo(customerId!!)


//        // DOWNLOAD IMAGE
//        val qrUrl = "https://samquicksal.com/images/samquicksalLogo.png"
//        btnCOGCCDownloadQr.setOnClickListener {
//            val request = DownloadManager.Request(Uri.parse(qrUrl))
//                .setTitle("GCash Qr Code")
//                .setDescription("Downloading...")
//                .setAllowedOverMetered(true)
//                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
//                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
//                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "test.png")
//
//            val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//            mydownloadid = dm.enqueue(request)
//        }
//        val br = object: BroadcastReceiver(){
//            override fun onReceive(context: Context?, intent: Intent?) {
//                val id: Long? = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
//                if(id == mydownloadid){
//                    Toast.makeText(applicationContext, "GCash QR Code Downloaded Successfully", Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//        registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

    }
}