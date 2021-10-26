package com.altwav.samquicksal2.notificationsActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.altwav.samquicksal2.LiveStatusActivity
import com.altwav.samquicksal2.R
import kotlinx.android.synthetic.main.activity_pending_notification.*

class PendingNotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_notification)

        btn_pending_notification_back.setOnClickListener {
            finish()
        }

        val status = "pending"
        if(status == "pending"){
            btn_pending_notification_status.setOnClickListener {
                val intent = Intent(this, LiveStatusActivity::class.java)
                startActivity(intent)
            }
        } else {
            btn_pending_notification_status.visibility = View.GONE
        }




    }
}