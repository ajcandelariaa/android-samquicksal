package com.altwav.samquicksal2

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.models.SubmitQueueFormModel
import com.altwav.samquicksal2.models.SubmitQueueFormModelResponse
import com.altwav.samquicksal2.viewmodel.SubmitQueueFormViewModel
import kotlinx.android.synthetic.main.activity_booking_details.*

class BookingDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: SubmitQueueFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)

        btn_booking_details_back.setOnClickListener {
            finish()
        }

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)
        Log.d("message", "$customerId")

        val bookType = intent.getStringExtra("bookType")
        val restaurantId = intent.getIntExtra("restaurantId", 0)
        val orderSetId = intent.getIntExtra("orderSetId", 0)
        val orderSetPrice = intent.getStringExtra("orderSetPrice")
        val orderSetName = intent.getStringExtra("orderSetName")
        val numberOfPersons = intent.getIntExtra("numberOfPersons", 0)
        val numberOfTables = intent.getIntExtra("numberOfTables", 0)
        val hoursOfStay = intent.getIntExtra("hoursOfStay", 0)
        val numberOfChildren = intent.getIntExtra("numberOfChildren", 0)
        val numberOfPwd = intent.getIntExtra("numberOfPwd", 0)
        val notes = intent.getStringExtra("notes")
        val rewardStatus = intent.getStringExtra("rewardStatus")
        val rewardType = intent.getStringExtra("rewardType")
        val rewardInput = intent.getIntExtra("rewardInput", 0)
        var date: String? = null
        var time: String? = null

        if(bookType == "Reserve"){
            date = intent.getStringExtra("date")
            time = intent.getStringExtra("time")
            tvBookDetailsDate.text = date
            tvBookDetailsTime.text = time
        } else {
            llBookDetailsDate.visibility = ViewGroup.GONE
            llBookDetailsTime.visibility = ViewGroup.GONE
        }

        tvBookDetailsOrderSet.text = orderSetName
        tvBookDetailsPersons.text = numberOfPersons.toString()
        tvBookDetailsTables.text = numberOfTables.toString()
        tvBookDetailsHoursOfStay.text = hoursOfStay.toString()
        tvBookDetailsChildren.text = numberOfChildren.toString()
        tvBookDetailsPwd.text = numberOfPwd.toString()

        if(notes == ""){
            tvBookDetailsNotes.visibility = View.GONE
        } else {
            tvBookDetailsNotes.text = notes
        }

        tvBookDetailsOrderSet2.text = orderSetName
        tvBookDetailsPersons2.text = "${numberOfPersons}x"
        val orderTotal = orderSetPrice!!.toDouble() * numberOfPersons
        tvBookDetailsOrderTotal.text = "${String.format("%.2f", orderTotal)}"

        if (numberOfPwd == 0){
            tvBookDetailsPwd2.text = "0x"
            tvBookDetailsPwdTotal.text =  "0.00"
        } else {
            tvBookDetailsPwd2.text = "${numberOfPwd}x"
            val discount: Double = orderSetPrice.toDouble() * (numberOfPwd * 0.2)
            tvBookDetailsPwdTotal.text =  "${String.format("%.2f", discount)}"
        }

        if (numberOfChildren == 0){
            tvBookDetailsChildren2.text = "0x"
            tvBookDetailsChildrenTotal.text =  "0.00"
        } else {
            tvBookDetailsChildren2.text = "${numberOfChildren}x"
            val discount: Double = orderSetPrice.toDouble() * numberOfChildren
            tvBookDetailsChildrenTotal.text =  "${String.format("%.2f", discount)}"
        }

        llBookDetailsReward.visibility = View.GONE

        calculateTotal()
        if (rewardStatus == "Incomplete"){
            cbBookDetailsReward.visibility = View.GONE
        } else {
            cbBookDetailsReward.setOnClickListener {
                if(cbBookDetailsReward.isChecked){
                    llBookDetailsReward.visibility = ViewGroup.VISIBLE
                    val discount: Double
                    when(rewardType){
                        "DSCN" -> {
                            tvBookDetailsReward.text = "Reward: Discount ${rewardInput}% in a Total Bill"
                            discount = orderTotal * (rewardInput.toDouble() / 100)
                            tvBookDetailsRewardTotal.text = "${String.format("%.2f", discount)}"
                        }
                        "FRPE" -> {
                            tvBookDetailsReward.text = "Reward: Free ${rewardInput} person in a group"
                            discount = orderSetPrice.toDouble() * rewardInput.toDouble()
                            tvBookDetailsRewardTotal.text = "${String.format("%.2f", discount)}"
                        }
                        "HLF" -> {
                            tvBookDetailsReward.text = "Reward: Half in the group will be free"
                            discount = orderTotal - (orderTotal * 0.5)
                            tvBookDetailsRewardTotal.text = "${String.format("%.2f", discount)}"
                        }
                        "ALL" -> {
                            tvBookDetailsReward.text = "Reward: All people in the group will be free"
                            discount = orderTotal
                            tvBookDetailsRewardTotal.text = "${String.format("%.2f", discount)}"
                        }
                    }
                    calculateTotal2()
                } else {
                    llBookDetailsReward.visibility = ViewGroup.GONE
                    calculateTotal()
                }

            }
        }

        viewModel = ViewModelProvider(this).get(SubmitQueueFormViewModel::class.java)
        viewModel.getQueueFormObserver().observe(this, Observer <SubmitQueueFormModelResponse>{
            if(it == null){
                Toast.makeText(this, "Error Submitting Form", Toast.LENGTH_SHORT).show()
            } else {
                if(it.status == "Success"){
                    Toast.makeText(this,"Form Submitted", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LiveStatusActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("actType", "booking")
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "${it.status}", Toast.LENGTH_SHORT).show()
                }
            }
        })

        val totalPwdChild = numberOfChildren + numberOfPwd

        btnBookDetailsSubmit.setOnClickListener {
            val totalPrice = tvBookDetailsTotalPrice.text.toString().toDouble()
            var rewardClaimed: String? = null

            if (rewardStatus == "Complete"){
                if(cbBookDetailsReward.isChecked){
                    rewardClaimed = "Yes"
                } else {
                    rewardClaimed = "No"
                }
            }

            AlertDialog.Builder(this)
                .setTitle("Submit")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Are you sure you want to submit?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val customer = SubmitQueueFormModel(
                        customerId,
                        restaurantId,
                        orderSetId,
                        numberOfPersons,
                        numberOfTables,
                        hoursOfStay,
                        numberOfChildren,
                        numberOfPwd,
                        totalPwdChild,
                        notes,
                        rewardStatus,
                        rewardType,
                        rewardInput,
                        rewardClaimed,
                        totalPrice,
                    )
                    Log.d("message", "SUBMIT $customer")
                    viewModel.submitQueueForm(customer)
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                }
                .show()
        }

    }

    private fun calculateTotal(){
        val orderTotal = tvBookDetailsOrderTotal.text.toString().toDouble()
        val pwdTotal = tvBookDetailsPwdTotal.text.toString().toDouble()
        val childrenTotal = tvBookDetailsChildrenTotal.text.toString().toDouble()
        val finalTotalBill = orderTotal - (pwdTotal + childrenTotal)

        if(finalTotalBill < 0.00){
            tvBookDetailsTotalPrice.text = "0.00"
        } else {
            tvBookDetailsTotalPrice.text =  "${String.format("%.2f", finalTotalBill)}"
        }
    }

    private fun calculateTotal2(){
        val orderTotal = tvBookDetailsOrderTotal.text.toString().toDouble()
        val pwdTotal = tvBookDetailsPwdTotal.text.toString().toDouble()
        val childrenTotal = tvBookDetailsChildrenTotal.text.toString().toDouble()
        val rewardTotal = tvBookDetailsRewardTotal.text.toString().toDouble()
        val finalTotalBill = orderTotal - (pwdTotal + childrenTotal + rewardTotal)

        if(finalTotalBill < 0.00){
            tvBookDetailsTotalPrice.text = "0.00"
        } else {
            tvBookDetailsTotalPrice.text =  "${String.format("%.2f", finalTotalBill)}"
        }
    }
}