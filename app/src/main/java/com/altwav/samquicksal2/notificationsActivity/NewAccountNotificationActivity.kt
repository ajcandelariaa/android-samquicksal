package com.altwav.samquicksal2.notificationsActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altwav.samquicksal2.R
import kotlinx.android.synthetic.main.activity_new_account_notification.*

class NewAccountNotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account_notification)

        btn_newaccount_notification_back.setOnClickListener {
            finish()
        }
    }
}