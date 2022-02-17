package com.altwav.samquicksal2

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.*
import android.net.Uri
import android.os.*
import android.provider.OpenableColumns
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.altwav.samquicksal2.viewmodel.GCashStatusViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_gcash_checkout.*
import com.altwav.samquicksal2.viewmodel.UploadReceiptViewModell

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

import okhttp3.RequestBody
import okhttp3.MultipartBody


class GcashCheckoutActivity : AppCompatActivity() {

    private lateinit var viewModel: GCashStatusViewModel
    private lateinit var viewModel2: UploadReceiptViewModell
    private var mydownloadid: Long = 0

    private lateinit var requestImage: MultipartBody.Part
    private lateinit var cust_id: RequestBody

    private lateinit var book_id: String
    private lateinit var book_type: String

    private lateinit var book_id2: RequestBody
    private lateinit var book_type2: RequestBody

    private val loading = LoadingDialog(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gcash_checkout)
        clGcashSubmitReceipt.visibility = View.GONE
        loading.startLoading()

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null){
                tvCOGCCUploadImageLabel.visibility = View.GONE
                btnCOGCCUploadImage.setImageURI(uri)

                val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r", null)
                val inputStream = FileInputStream(parcelFileDescriptor!!.fileDescriptor)
                val file = File(cacheDir, contentResolver.getFileName(uri))
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)

                val body = UploadRequestBody(file, "image")
                requestImage = MultipartBody.Part.createFormData("gCashReceipt", file.name, body)
                cust_id = customerId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
                book_id2 = book_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                book_type2 = book_type.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            } else {
                tvCOGCCUploadImageLabel.visibility = View.VISIBLE
                Glide.with(this).load(R.drawable.upload_icon).into(btnCOGCCUploadImage)
            }
        }

        viewModel2 = ViewModelProvider(this).get<UploadReceiptViewModell>()
        viewModel2.getUploadReceiptObserver().observe(this, {
            if (it != null) {
                if(it.status == "Success"){
                    Toast.makeText(this, "Receipt Uploaded Successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, CheckoutStatusActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
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

                // BOOKING DETAILS
                book_id = it.book_id.toString()
                book_type = it.book_type.toString()

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
                    if(tvCOGCCUploadImageLabel.visibility == View.VISIBLE){
                        Toast.makeText(applicationContext, "Please choose an image first", Toast.LENGTH_SHORT).show()
                    } else {
                        AlertDialog.Builder(this)
                            .setTitle("Upload Receipt")
                            .setIcon(R.mipmap.ic_launcher)
                            .setMessage("Are you sure you want upload your receipt?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                viewModel2.getUploadReceiptInfo(requestImage, cust_id, book_id2, book_type2)
                            }
                            .setNegativeButton("No") { dialog, id ->
                                dialog.cancel()
                            }
                            .show()
                    }
                }
                clGcashSubmitReceipt.visibility = View.VISIBLE
                loading.isDismiss()
            }
        })
        viewModel.getGCashStatusInfo(customerId!!)
    }

    fun ContentResolver.getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }
}