package com.altwav.samquicksal2.notificationsActivity

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.LoadingDialog
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.QrReqAppDecModel
import com.altwav.samquicksal2.viewmodel.NotifQrValidateViewModel
import com.altwav.samquicksal2.viewmodel.QrReqAppDecViewModel
import kotlinx.android.synthetic.main.activity_qrscanned_notification.*
import kotlinx.android.synthetic.main.activity_queue_form.*
import kotlinx.android.synthetic.main.activity_scan_qr_code.*

class QRScannedNotificationActivity : AppCompatActivity() {
    private lateinit var viewModel: NotifQrValidateViewModel
    private lateinit var viewModel2: QrReqAppDecViewModel
    private val loading = LoadingDialog(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscanned_notification)

        clQRScannedNotification.visibility = View.GONE
        loading.startLoading()

        btn_qrscanned_notification_back.setOnClickListener {
            finish()
        }

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val cust_id = sharedPreferences.getInt("CUSTOMER_ID", 0)
        val notif_id = intent.getIntExtra("notificationId", 0)

        viewModel2 = ViewModelProvider(this).get(QrReqAppDecViewModel::class.java)
        viewModel2.getQrReqAppDecObserver().observe(this, {
            if (it != null) {
                Toast.makeText(this, "${it.status}", Toast.LENGTH_LONG).show()
                finish()
            }
        })

        viewModel = ViewModelProvider(this).get(NotifQrValidateViewModel::class.java)
        viewModel.getNotifQrValidateObserver().observe(this, {
            clQRScannedNotification.visibility = View.VISIBLE
            loading.isDismiss()
            if(it != null){
                tvNotifQrVCustName.text = "Name: ${it.custName}"
                tvNotifQrVCustEmail.text = "Email Address: ${it.custEAddress}"
                tvNotifQrVCustCNum.text = "Contact Number: ${it.custENumber}"
                tvNotifQrVStatus.text = "Status: ${it.scannedStatus}"
                tvNotifQrVDate.text = "Request Date: ${it.scanReqDate}"
                tvNotifQrVTime.text = "Request Time: ${it.scanReqTime}"
                var custQrAcces_id = it.custQrAcces_id
                if(it.tableNumbers == null){
                    llCTBNumber.visibility = View.GONE
                } else {
                    llCTBNumber.visibility = View.VISIBLE

                    val tableNumbers = it.tableNumbers.toTypedArray()
                    val tableNumbersAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tableNumbers)
                    sNotifQrVTNumbers.adapter = tableNumbersAdapter

                    btnNotifQrVDecline.setOnClickListener {
                        AlertDialog.Builder(this)
                            .setTitle("Decline Request?")
                            .setIcon(R.mipmap.ic_launcher)
                            .setMessage("If you declined, this customer will not gain access on your orders")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                val custReq = QrReqAppDecModel(custQrAcces_id, "declined")
                                viewModel2.getQrReqAppDecInfo(custReq)
                            }
                            .setNegativeButton("No") { dialog, id ->
                                dialog.cancel()
                            }
                            .show()
                    }

                    btnNotifQrVApprove.setOnClickListener {
                        AlertDialog.Builder(this)
                            .setTitle("Approved Request?")
                            .setIcon(R.mipmap.ic_launcher)
                            .setMessage("If you approve, this customer will gain access on your orders.")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                val choosenTableNumber = sNotifQrVTNumbers.selectedItem.toString()
                                val custReq = QrReqAppDecModel(custQrAcces_id, "approved", choosenTableNumber)
                                viewModel2.getQrReqAppDecInfo(custReq)
                            }
                            .setNegativeButton("No") { dialog, id ->
                                dialog.cancel()
                            }
                            .show()
                    }

                }
            }
        })

        viewModel.getNotifQrValidateInfo(cust_id, notif_id)


    }
}