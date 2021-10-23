package com.altwav.samquicksal2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_reservation_form.*

class ReservationFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_form)

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
        spinnerReserveFormHoursOfStay.adapter = arrayAdapter

        btn_reserve_form_back.setOnClickListener {
            finish()
        }

        cbReserveFormChildren.setOnClickListener {
            if(cbReserveFormChildren.isChecked){
                etReserveFormNumberOfChildren.isFocusable = true
                etReserveFormNumberOfChildren.isFocusableInTouchMode = true
                etReserveFormNumberOfChildren.isClickable = true
                etReserveFormNumberOfChildren.setBackgroundResource(R.drawable.edit_text_queue_form)
            } else {
                etReserveFormNumberOfChildren.setText("")
                etReserveFormNumberOfChildren.isFocusable = false
                etReserveFormNumberOfChildren.isFocusableInTouchMode = false
                etReserveFormNumberOfChildren.isClickable = false
                etReserveFormNumberOfChildren.setBackgroundResource(R.drawable.edit_text_queue_form_disabled)
            }
        }


        cbReserveFormSenior.setOnClickListener {
            if(cbReserveFormSenior.isChecked){
                etReserveFormNumberOfPwd.isFocusable = true
                etReserveFormNumberOfPwd.isFocusableInTouchMode = true
                etReserveFormNumberOfPwd.isClickable = true
                etReserveFormNumberOfPwd.setBackgroundResource(R.drawable.edit_text_queue_form)
            } else {
                etReserveFormNumberOfPwd.setText("")
                etReserveFormNumberOfPwd.isFocusable = false
                etReserveFormNumberOfPwd.isFocusableInTouchMode = false
                etReserveFormNumberOfPwd.isClickable = false
                etReserveFormNumberOfPwd.setBackgroundResource(R.drawable.edit_text_queue_form_disabled)
            }
        }

        tvReserveFormOrderSet.text = orderSetName

        if(rTimeLimit == 0){
            tvReserveFormHoursOfStay.visibility = View.GONE
        } else {
            tvReserveFormHoursOfStayLabel.text = "Hours of Stay"
            spinnerReserveFormHoursOfStay.visibility = View.GONE
            tvReserveFormHoursOfStay.text = "$rTimeLimit hours"

            val constraintLayout: ConstraintLayout = clReserveForm
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)
            constraintSet.connect(R.id.linearLayout112, ConstraintSet.TOP, R.id.tvReserveFormHoursOfStay, ConstraintSet.BOTTOM,30)
            constraintSet.connect(R.id.linearLayout112, ConstraintSet.END, R.id.tvReserveFormHoursOfStay, ConstraintSet.END)
            constraintSet.connect(R.id.linearLayout112, ConstraintSet.START, R.id.tvReserveFormHoursOfStay, ConstraintSet.START)
            constraintSet.applyTo(constraintLayout)
        }

        btnReserveFormNext.setOnClickListener {
            val numberOfPersons = etReserveFormNumberOfPeople.text.toString()
            var numberOfTables: Int? = null
            var numberOfChildren: Int? = null
            var numberOfPwd: Int? = null
            val notes = etReserveFormNotes.text.toString()
            var countError: Int = 0

            if(rTimeLimit == 0){
                when (spinnerReserveFormHoursOfStay.selectedItem.toString()){
                    "2 hours" -> finalHoursOfStay = 2
                    "3 hours" -> finalHoursOfStay = 3
                    "4 hours" -> finalHoursOfStay = 4
                    "5 hours" -> finalHoursOfStay = 5
                }
            } else {
                finalHoursOfStay = rTimeLimit
            }

            if(numberOfPersons.isEmpty()){
                etReserveFormNumberOfPeople.error = "Number of Persons is required"
                countError += 1
                return@setOnClickListener
            } else {
                if(numberOfPersons.toInt() <= rCapacityPerTable){
                    numberOfTables = 1
                } else {
                    numberOfTables = numberOfPersons.toInt() / rCapacityPerTable
                }
            }

            if(cbReserveFormChildren.isChecked){
                if(etReserveFormNumberOfChildren.text.isEmpty()){
                    numberOfChildren = 0
                } else {
                    numberOfChildren = etReserveFormNumberOfChildren.text.toString().toInt()
                    if (numberOfChildren > numberOfPersons.toInt()){
                        etReserveFormNumberOfChildren.error = "Must not exceed to Number of Persons"
                        countError += 1
                        return@setOnClickListener
                    }
                }
            } else {
                numberOfChildren = 0
            }

            if(cbReserveFormSenior.isChecked){
                if(etReserveFormNumberOfPwd.text.isEmpty()){
                    numberOfPwd = 0
                } else {
                    numberOfPwd = etReserveFormNumberOfPwd.text.toString().toInt()
                    if (numberOfPwd > numberOfPersons.toInt()){
                        etReserveFormNumberOfPwd.error = "Must not exceed to Number of Persons"
                        countError += 1
                        return@setOnClickListener
                    }
                }
            } else {
                numberOfPwd = 0
            }

            if (numberOfChildren > 0 && numberOfPwd > 0){
                if( (numberOfChildren + numberOfPwd) > numberOfPersons.toInt() ){
                    etReserveFormNumberOfPeople.error = "Must equal the sum of Children and Senior Citizen"
                    countError += 1
                    return@setOnClickListener
                }
            }

            if(countError == 0){
                val intent = Intent(this, BookingDetailsActivity::class.java)
                intent.putExtra("bookType", "Reserve")
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