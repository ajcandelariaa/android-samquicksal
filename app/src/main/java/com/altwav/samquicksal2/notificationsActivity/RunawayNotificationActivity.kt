package com.altwav.samquicksal2.notificationsActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altwav.samquicksal2.R
import kotlinx.android.synthetic.main.activity_runaway_notification.*

class RunawayNotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_runaway_notification)

        btn_runaway_notification_back.setOnClickListener {
            finish()
        }
    }
}