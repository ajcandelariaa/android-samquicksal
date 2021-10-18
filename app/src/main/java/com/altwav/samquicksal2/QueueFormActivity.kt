package com.altwav.samquicksal2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_queue_form.*
import android.widget.ArrayAdapter

class QueueFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queue_form)

        btn_queue_form_back.setOnClickListener {
            finish()
        }

        val hours = arrayOf("2 hours", "3 hours", "4 hours", "5 hours")

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, hours)
        spinner_hours_of_stay.adapter = arrayAdapter

    }
}