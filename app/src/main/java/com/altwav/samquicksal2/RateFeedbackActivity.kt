package com.altwav.samquicksal2

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.altwav.samquicksal2.models.OrderingAssistanceModel
import com.altwav.samquicksal2.models.RFSubmitFormModel
import com.altwav.samquicksal2.viewmodel.OrderingCheckoutViewModel
import com.altwav.samquicksal2.viewmodel.RFStatusViewModel
import com.altwav.samquicksal2.viewmodel.RFSubmitFormViewModel
import kotlinx.android.synthetic.main.activity_rate_feedback.*
import kotlinx.android.synthetic.main.activity_register.*

class RateFeedbackActivity : AppCompatActivity() {
    private lateinit var viewModel: RFStatusViewModel
    private lateinit var viewModel2: RFSubmitFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_feedback)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)

        viewModel = ViewModelProvider(this).get<RFStatusViewModel>()
        viewModel.getRFStatusObserver().observe(this, {
            if (it != null){
                tvRFRName.text = "for Dining at ${it.rName}"
            }
        })

        viewModel.getRFStatusInfo(customerId!!)

        rbRFRatingStar.rating = 2.5f
        rbRFRatingStar.stepSize = .5f

        rbRFRatingStar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            tvRFRatingText.text = "$rating"
        }

        viewModel2 = ViewModelProvider(this).get()
        viewModel2.getRFSubmitFormObserver().observe(this,  {
            if(it != null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Feedback Submitted!", Toast.LENGTH_SHORT).show()
            }
        })


        btnRatingNotNow.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Not Now")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Are you sure you don't want to give them a feedback?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val rfForm = RFSubmitFormModel(customerId, "", "", "", "not now")
                    viewModel2.getRFSubmitFormInfo(rfForm)
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                }
                .show()
        }

        btnRatingSubmit.setOnClickListener {
            val comment = etRFComment.text

            if(comment.isEmpty()){
                etRFComment.error = "Comment is Required"
                return@setOnClickListener
            } else {
                var finalAnonymous: String

                if(sRFAnonymously.isChecked){
                    finalAnonymous = "Yes"
                } else {
                    finalAnonymous = "No"
                }

                AlertDialog.Builder(this)
                    .setTitle("Submit Feedback")
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage("Are you sure you want to give them a feedback?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        val rfForm = RFSubmitFormModel(customerId, tvRFRatingText.text.toString(), comment.toString(), finalAnonymous, "submit")
                        viewModel2.getRFSubmitFormInfo(rfForm)
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.cancel()
                    }
                    .show()
            }
        }
    }
}