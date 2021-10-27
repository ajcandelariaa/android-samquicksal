package com.altwav.samquicksal2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.models.ForgotPasswordModel
import com.altwav.samquicksal2.models.ForgotPasswordModelResponse
import com.altwav.samquicksal2.models.RegisterCustomerModelResponse
import com.altwav.samquicksal2.viewmodel.ForgotPasswordViewModel
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var viewModel: ForgotPasswordViewModel
    private val validEmail = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btnFPGoBack.setOnClickListener {
            finish()
        }

        viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        viewModel.getForgotPasswordObserver().observe(this, Observer <ForgotPasswordModelResponse>{
            if(it == null){
                Toast.makeText(this, "Error Submitting Request", Toast.LENGTH_SHORT).show()
            } else {
                if(it.status == "emailNotExist"){
                    Toast.makeText(this, "Email does not exist", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "We've emailed you the reset password link", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        })

        btnFPSubmit.setOnClickListener {
            val emailAddress = etFPEmailAddress.text.toString()
            var countError: Int = 0

            // EMAIL ADDRESS VALIDATION
            if (!validEmail.matcher(emailAddress).matches()){
                etFPEmailAddress.error = "Must be a valid Email Address"
                etFPEmailAddress.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError++
                return@setOnClickListener
            } else { etFPEmailAddress.setBackgroundResource(R.drawable.edit_text_rounded) }
            if (emailAddress.isEmpty()){
                etFPEmailAddress.error = "Email Address is Required"
                etFPEmailAddress.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError++
                return@setOnClickListener
            } else { etFPEmailAddress.setBackgroundResource(R.drawable.edit_text_rounded) }


            // CHECK ERRORS
            if(countError == 0) {
                val customer = ForgotPasswordModel(emailAddress)
                viewModel.getForgotPasswordInfo(customer)
            }
        }
    }
}