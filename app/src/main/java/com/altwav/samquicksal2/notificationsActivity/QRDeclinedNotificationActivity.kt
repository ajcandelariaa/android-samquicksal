package com.altwav.samquicksal2.notificationsActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altwav.samquicksal2.R
import kotlinx.android.synthetic.main.activity_qrdeclined_notification.*

class QRDeclinedNotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrdeclined_notification)

        btn_qrdeclined_notification_back.setOnClickListener {
            finish()
        }
    }
}