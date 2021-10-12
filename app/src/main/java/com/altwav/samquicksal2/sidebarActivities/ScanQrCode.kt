package com.altwav.samquicksal2.sidebarActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altwav.samquicksal2.R
import kotlinx.android.synthetic.main.activity_scan_qr_code.*

class ScanQrCode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr_code)
        supportActionBar?.hide()

        btn_scan_back.setOnClickListener {
            finish()
        }
    }
}