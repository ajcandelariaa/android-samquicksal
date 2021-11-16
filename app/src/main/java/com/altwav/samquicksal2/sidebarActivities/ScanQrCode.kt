package com.altwav.samquicksal2.sidebarActivities

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.viewmodel.QRScannedViewModel
import com.altwav.samquicksal2.viewmodel.QrReqAccessViewModel
import com.altwav.samquicksal2.viewmodel.RFStatusViewModel
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_checkout_status.*
import kotlinx.android.synthetic.main.activity_rate_feedback.*
import kotlinx.android.synthetic.main.activity_scan_qr_code.*

private const val CAMERA_REQUEST_CODE = 101

class ScanQrCode : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var viewModel: QRScannedViewModel
    private lateinit var viewModel2: QrReqAccessViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr_code)
        btn_scan_back.setOnClickListener {
            finish()
        }
        llScannedInfoContainer.visibility = View.GONE
        setupPermissions()
        codeScanner()


        viewModel = ViewModelProvider(this).get<QRScannedViewModel>()
        viewModel.getQrScannedObserver().observe(this, {
            if (it != null){
                if(it.reqStatus == "Invalid Access"){
                    Toast.makeText(applicationContext, "${it.reqStatus}", Toast.LENGTH_LONG).show()
                } else if(it.reqStatus == "Ongoing Status") {
                    Toast.makeText(applicationContext, "${it.reqStatus}", Toast.LENGTH_LONG).show()
                } else if (it.reqStatus == "Approved"){
                    llScannedInfoContainer.visibility = View.VISIBLE
                    btn_request_access.visibility = View.GONE
                    Glide.with(this).load(it.custImage).into(ivQrSRCusImage)
                    tvQrSRCusName.text = it.custName
                    tvQrSRCusEmail.text = it.custEAddress
                    tvQrSRCusCNum.text = it.custCNumber
                    tvQrSRCusDJoined.text = it.custJDate
                    tvQrSRRestName.text = it.bdRName
                    tvQrSRRestAddress.text = it.bdRAddres
                    tvQrSROrderName.text = it.bdOrderName
                    tvQrSRBookType.text = it.bdBookType
                    tvQrSRSlot.text = it.bdAccessSlot.toString()
                    Toast.makeText(applicationContext, "You already have an access to this customer", Toast.LENGTH_LONG).show()
                } else if (it.reqStatus == "Full Slot"){
                    llScannedInfoContainer.visibility = View.VISIBLE
                    btn_request_access.visibility = View.GONE
                    Glide.with(this).load(it.custImage).into(ivQrSRCusImage)
                    tvQrSRCusName.text = it.custName
                    tvQrSRCusEmail.text = it.custEAddress
                    tvQrSRCusCNum.text = it.custCNumber
                    tvQrSRCusDJoined.text = it.custJDate
                    tvQrSRRestName.text = it.bdRName
                    tvQrSRRestAddress.text = it.bdRAddres
                    tvQrSROrderName.text = it.bdOrderName
                    tvQrSRBookType.text = it.bdBookType
                    tvQrSRSlot.text = it.bdAccessSlot.toString()
                    Toast.makeText(applicationContext, "Customer is already Full Slot", Toast.LENGTH_LONG).show()
                } else if (it.reqStatus == "Success"){
                    llScannedInfoContainer.visibility = View.VISIBLE
                    btn_request_access.visibility = View.VISIBLE
                    Glide.with(this).load(it.custImage).into(ivQrSRCusImage)
                    tvQrSRCusName.text = it.custName
                    tvQrSRCusEmail.text = it.custEAddress
                    tvQrSRCusCNum.text = it.custCNumber
                    tvQrSRCusDJoined.text = it.custJDate
                    tvQrSRRestName.text = it.bdRName
                    tvQrSRRestAddress.text = it.bdRAddres
                    tvQrSROrderName.text = it.bdOrderName
                    tvQrSRBookType.text = it.bdBookType
                    tvQrSRSlot.text = it.bdAccessSlot.toString()
                    val main_cust_id = it.custId
                    btn_request_access.setOnClickListener {
                        AlertDialog.Builder(this)
                            .setTitle("Request Access")
                            .setIcon(R.mipmap.ic_launcher)
                            .setMessage("Are you sure you want to participate ordering with ${tvQrSRCusName.text}?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                viewModel2 = ViewModelProvider(this).get<QrReqAccessViewModel>()
                                viewModel2.getQrReqAccessObserver().observe(this, { it2 ->
                                    if(it2 != null){
                                        if(it2.status == "success"){
                                            Toast.makeText(applicationContext, "Request Sent! Please wait for your friend to validate your request", Toast.LENGTH_LONG).show()
                                            finish()
                                        } else {
                                            Toast.makeText(applicationContext, "Request Denied", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                                    }
                                })
                                val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                                val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)
                                if (customerId != null && main_cust_id != null) {
                                    viewModel2.getQrReqAccessInfo(customerId, main_cust_id)
                                }
                            }
                            .setNegativeButton("No") { dialog, id ->
                                dialog.cancel()
                            }
                            .show()
                    }
                    Toast.makeText(applicationContext, "Scanned Successfully", Toast.LENGTH_LONG).show()
                } else if (it.reqStatus == "Pending"){
                    llScannedInfoContainer.visibility = View.VISIBLE
                    btn_request_access.visibility = View.GONE
                    Glide.with(this).load(it.custImage).into(ivQrSRCusImage)
                    tvQrSRCusName.text = it.custName
                    tvQrSRCusEmail.text = it.custEAddress
                    tvQrSRCusCNum.text = it.custCNumber
                    tvQrSRCusDJoined.text = it.custJDate
                    tvQrSRRestName.text = it.bdRName
                    tvQrSRRestAddress.text = it.bdRAddres
                    tvQrSROrderName.text = it.bdOrderName
                    tvQrSRBookType.text = it.bdBookType
                    tvQrSRSlot.text = it.bdAccessSlot.toString()
                    Toast.makeText(applicationContext, "You've already sent a request to this customer a while ago. Please wait for the customer to validate your request", Toast.LENGTH_LONG).show()
                } else {

                }
            }
        })



    }

    private fun codeScanner(){
        codeScanner = CodeScanner(this, scanner_view)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                    val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)
                    if (customerId != null) {
                        viewModel.getQrScannedInfo(customerId, it.text.toInt())
                    }
                    stopPreview()
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Camera Initialization error: ${it.message}")
                }
            }
        }
        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            CAMERA_REQUEST_CODE -> {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "You need the camera to be able to scan qr code!", Toast.LENGTH_SHORT).show()
                } else {
                    // success
                }
            }
        }
    }
}