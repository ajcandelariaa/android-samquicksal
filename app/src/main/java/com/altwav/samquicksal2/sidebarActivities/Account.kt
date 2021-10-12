package com.altwav.samquicksal2.sidebarActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altwav.samquicksal2.R
import kotlinx.android.synthetic.main.activity_account.*

class Account : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        supportActionBar?.hide()

        btn_account_back.setOnClickListener {
            finish()
        }
    }
}