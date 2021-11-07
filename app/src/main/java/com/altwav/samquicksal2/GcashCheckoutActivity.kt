package com.altwav.samquicksal2

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.altwav.samquicksal2.viewmodel.GCashStatusViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_gcash_checkout.*
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import com.altwav.samquicksal2.viewmodel.UploadReceiptViewModell
import java.io.ByteArrayOutputStream
import java.util.*

class GcashCheckoutActivity : AppCompatActivity() {

    private lateinit var viewModel: GCashStatusViewModel
    private lateinit var viewModel2: UploadReceiptViewModell
    private var mydownloadid: Long = 0
    private lateinit var bitmap: Bitmap
    private var decodeImg: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gcash_checkout)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)

        val getContent = registerForActivityResult(GetContent()) { uri: Uri? ->
            if (uri != null){
                tvCOGCCUploadImageLabel.visibility = View.GONE
                btnCOGCCUploadImage.setImageURI(uri)
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

                val byte = byteArrayOutputStream.toByteArray()
                decodeImg = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Base64.getEncoder().encodeToString(byte)
                } else {
                    TODO("VERSION.SDK_INT < O")
                }

            } else {
                tvCOGCCUploadImageLabel.visibility = View.VISIBLE
                Glide.with(this).load(R.drawable.upload_icon).into(btnCOGCCUploadImage)
            }
        }
        viewModel2 = ViewModelProvider(this).get<UploadReceiptViewModell>()
        viewModel2.getUploadReceiptObserver().observe(this, {
            if (it != null) {
                if(it.status == "Success"){
                    val intent = Intent(this, CheckoutStatusActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(applicationContext, "GCash Receipt submitted successfully! Please wait til your payment is validated", Toast.LENGTH_LONG).show()
                }
                Toast.makeText(applicationContext, "${it.status}", Toast.LENGTH_LONG).show()
            }
            Toast.makeText(applicationContext, "${it}", Toast.LENGTH_LONG).show()
        })

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
                    else -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                // DOWNLOAD IMAGE
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

                // SELECT IMAGE
                btnCOGCCUploadImage.setOnClickListener {
                    getContent.launch("image/*")
                }

                btnCOGCCSubmitReceipt.setOnClickListener {
                    if(decodeImg == ""){
                        Toast.makeText(applicationContext, "Please choose an image first", Toast.LENGTH_SHORT).show()
                    } else {
                        AlertDialog.Builder(this)
                            .setTitle("Upload Receipt")
                            .setIcon(R.mipmap.ic_launcher)
                            .setMessage("Are you sure you want upload your receipt?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                Toast.makeText(applicationContext, "Under Construction", Toast.LENGTH_LONG).show()
//                                viewModel2.getUploadReceiptInfo(decodeImg, customerId!!)
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
    }
}