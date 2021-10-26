package com.altwav.samquicksal2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_queue_form.*
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.altwav.samquicksal2.sidebarActivities.Account
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_rewards.view.*

class QueueFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queue_form)

        val restaurantId = intent.getIntExtra("restaurantId", 0)
        val rTimeLimit = intent.getIntExtra("rTimeLimit", 0)
        val rCapacityPerTable = intent.getIntExtra("rCapacityPerTable", 0)
        val orderSetId = intent.getIntExtra("orderSetId", 0)
        val orderSetPrice = intent.getStringExtra("orderSetPrice")
        val orderSetName = intent.getStringExtra("orderSetName")
        val rewardStatus = intent.getStringExtra("rewardStatus")
        val rewardType = intent.getStringExtra("rewardType")
        val rewardInput = intent.getIntExtra("rewardInput", 0)
        var finalHoursOfStay: Int? = null

        val hours = arrayOf("2 hours", "3 hours", "4 hours", "5 hours")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, hours)
        spinnerQueueFormHoursOfStay.adapter = arrayAdapter

        btn_queue_form_back.setOnClickListener {
            finish()
        }

        cbQueueFormChildren.setOnClickListener {
            if(cbQueueFormChildren.isChecked){
                etQueueFormNumberOfChildren.isFocusable = true
                etQueueFormNumberOfChildren.isFocusableInTouchMode = true
                etQueueFormNumberOfChildren.isClickable = true
                etQueueFormNumberOfChildren.setBackgroundResource(R.drawable.edit_text_queue_form)
            } else {
                etQueueFormNumberOfChildren.setText("")
                etQueueFormNumberOfChildren.isFocusable = false
                etQueueFormNumberOfChildren.isFocusableInTouchMode = false
                etQueueFormNumberOfChildren.isClickable = false
                etQueueFormNumberOfChildren.setBackgroundResource(R.drawable.edit_text_queue_form_disabled)
            }
        }


        cbQueueFormSenior.setOnClickListener {
            if(cbQueueFormSenior.isChecked){
                etQueueFormNumberOfPwd.isFocusable = true
                etQueueFormNumberOfPwd.isFocusableInTouchMode = true
                etQueueFormNumberOfPwd.isClickable = true
                etQueueFormNumberOfPwd.setBackgroundResource(R.drawable.edit_text_queue_form)
            } else {
                etQueueFormNumberOfPwd.setText("")
                etQueueFormNumberOfPwd.isFocusable = false
                etQueueFormNumberOfPwd.isFocusableInTouchMode = false
                etQueueFormNumberOfPwd.isClickable = false
                etQueueFormNumberOfPwd.setBackgroundResource(R.drawable.edit_text_queue_form_disabled)
            }
        }

        tvQueueFormOrderSet.text = orderSetName

        if(rTimeLimit == 0){
            tvQueueFormHoursOfStay.visibility = View.GONE
        } else {
            tvQueueFormHoursOfStayLabel.text = "Hours of Stay"
            spinnerQueueFormHoursOfStay.visibility = View.GONE
            tvQueueFormHoursOfStay.text = "$rTimeLimit hours"

            val constraintLayout: ConstraintLayout = clQueueForm
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)
            constraintSet.connect(R.id.linearLayout11, ConstraintSet.TOP, R.id.tvQueueFormHoursOfStay, ConstraintSet.BOTTOM,30)
            constraintSet.connect(R.id.linearLayout11, ConstraintSet.END, R.id.tvQueueFormHoursOfStay, ConstraintSet.END)
            constraintSet.connect(R.id.linearLayout11, ConstraintSet.START, R.id.tvQueueFormHoursOfStay, ConstraintSet.START)
            constraintSet.applyTo(constraintLayout)
        }

        btnQueueFormNext.setOnClickListener {
            var numberOfPersons = etQueueFormNumberOfPeople.text.toString()
            var numberOfTables: Int? = null
            var numberOfChildren: Int? = null
            var numberOfPwd: Int? = null
            val notes = etQueueFormNotes.text.toString()
            var countError: Int = 0

            if(rTimeLimit == 0){
                when (spinnerQueueFormHoursOfStay.selectedItem.toString()){
                    "2 hours" -> finalHoursOfStay = 2
                    "3 hours" -> finalHoursOfStay = 3
                    "4 hours" -> finalHoursOfStay = 4
                    "5 hours" -> finalHoursOfStay = 5
                }
            } else {
                finalHoursOfStay = rTimeLimit
            }

            if(numberOfPersons.isEmpty()){
                etQueueFormNumberOfPeople.error = "Number of Persons is required"
                countError += 1
                return@setOnClickListener
            } else {
                if(numberOfPersons.toInt() <= rCapacityPerTable){
                    numberOfTables = 1
                } else {
                    numberOfTables = numberOfPersons.toInt() / rCapacityPerTable
                    val getModule = numberOfPersons.toInt() % rCapacityPerTable
                    if(getModule > 0){
                        numberOfTables += 1
                    }
                }
            }

            if(cbQueueFormChildren.isChecked){
                if(etQueueFormNumberOfChildren.text.isEmpty()){
                    numberOfChildren = 0
                } else {
                    numberOfChildren = etQueueFormNumberOfChildren.text.toString().toInt()
                    if (numberOfChildren > numberOfPersons.toInt()){
                        etQueueFormNumberOfChildren.error = "Must not exceed to Number of Persons"
                        countError += 1
                        return@setOnClickListener
                    }
                }
            } else {
                numberOfChildren = 0
            }

            if(cbQueueFormSenior.isChecked){
                if(etQueueFormNumberOfPwd.text.isEmpty()){
                    numberOfPwd = 0
                } else {
                    numberOfPwd = etQueueFormNumberOfPwd.text.toString().toInt()
                    if (numberOfPwd > numberOfPersons.toInt()){
                        etQueueFormNumberOfPwd.error = "Must not exceed to Number of Persons"
                        countError += 1
                        return@setOnClickListener
                    }
                }
            } else {
                numberOfPwd = 0
            }

            if (numberOfChildren > 0 && numberOfPwd > 0){
                if( (numberOfChildren + numberOfPwd) > numberOfPersons.toInt() ){
                    etQueueFormNumberOfPeople.error = "Must equal the sum of Children and Senior Citizen"
                    countError += 1
                    return@setOnClickListener
                }
            }

            if(countError == 0){
                val intent = Intent(this, BookingDetailsActivity::class.java)
                intent.putExtra("bookType", "Queue")
                intent.putExtra("restaurantId", restaurantId)
                intent.putExtra("orderSetId", orderSetId)
                intent.putExtra("orderSetPrice", orderSetPrice)
                intent.putExtra("orderSetName", orderSetName)
                intent.putExtra("numberOfPersons", numberOfPersons.toInt())
                intent.putExtra("numberOfTables", numberOfTables)
                intent.putExtra("hoursOfStay", finalHoursOfStay)
                intent.putExtra("numberOfChildren", numberOfChildren)
                intent.putExtra("numberOfPwd", numberOfPwd)
                intent.putExtra("notes", notes)
                intent.putExtra("rewardStatus", rewardStatus)
                intent.putExtra("rewardType", rewardType)
                intent.putExtra("rewardInput", rewardInput)
                startActivity(intent)
            }
        }
    }
}