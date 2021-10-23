package com.altwav.samquicksal2.notificationsActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altwav.samquicksal2.R
import kotlinx.android.synthetic.main.activity_no_show_notification.*

class NoShowNotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_show_notification)

        btn_noshow_notification_back.setOnClickListener {
            finish()
        }
    }
}