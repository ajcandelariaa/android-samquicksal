package com.altwav.samquicksal2.notificationsActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altwav.samquicksal2.R
import kotlinx.android.synthetic.main.activity_approved_notification_activity.*

class ApprovedNotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approved_notification_activity)

        btn_approved_notification_back.setOnClickListener {
            finish()
        }



    }
}