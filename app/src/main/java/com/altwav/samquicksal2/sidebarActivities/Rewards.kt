package com.altwav.samquicksal2.sidebarActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altwav.samquicksal2.R
import kotlinx.android.synthetic.main.activity_rewards.*

class Rewards : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards)
        supportActionBar?.hide()

        btn_rewards_back.setOnClickListener{
            finish()
        }
    }
}