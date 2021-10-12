package com.altwav.samquicksal2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.models.RegisterCustomerModel
import com.altwav.samquicksal2.models.RegisterCustomerModelResponse
import com.altwav.samquicksal2.viewmodel.RegisterCustomerViewModel
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern


class Register : AppCompatActivity() {
    private lateinit var viewModel: RegisterCustomerViewModel

    private val validEmail = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    private val validFullName = Pattern.compile("^[a-zA-Z\\s]+")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tvLogin.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this).get(RegisterCustomerViewModel::class.java)
        viewModel.getRegisterCustomerObserver().observe(this, Observer <RegisterCustomerModelResponse>{
            if(it == null){
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
            } else {
                if(it.status == "Registered Successfully"){
                    Toast.makeText(this, "${it.status}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("id", it.id)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "${it.status}", Toast.LENGTH_SHORT).show()
                }
            }
        })

        btnSignup.setOnClickListener {
            val fullName = etRName.text.toString()
            val emailAddress = etREmailAddress.text.toString()
            val contactNumber = etRContactNumber.text.toString()
            val password = etRPassword.text.toString()
            val confirmPassword = etRConfirmPassword.text.toString()

            var countError: Int = 0

            // FULL NAME VALIDATION
            if(fullName.isEmpty()){
                etRName.error = "Full Name is Required"
                etRName.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError++
                return@setOnClickListener
            } else { etRName.setBackgroundResource(R.drawable.edit_text_rounded) }
            if (!validFullName.matcher(fullName).matches()){
                etRName.error = "Full Name must contain letters only"
                etRName.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError++
                return@setOnClickListener
            } else { etRName.setBackgroundResource(R.drawable.edit_text_rounded) }



            // EMAIL ADDRESS VALIDATION
            if (!validEmail.matcher(emailAddress).matches()){
                etREmailAddress.error = "Must be a valid Email Address"
                etREmailAddress.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError++
                return@setOnClickListener
            } else { etREmailAddress.setBackgroundResource(R.drawable.edit_text_rounded) }
            if (emailAddress.isEmpty()){
                etREmailAddress.error = "Email Address is Required"
                etREmailAddress.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError++
                return@setOnClickListener
            } else { etREmailAddress.setBackgroundResource(R.drawable.edit_text_rounded) }



            // CONTACT NUMBER VALIDATION
            if (contactNumber.isEmpty()){
                etRContactNumber.error = "Contact Number is Required"
                etRContactNumber.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError++
                return@setOnClickListener
            } else { etRContactNumber.setBackgroundResource(R.drawable.edit_text_rounded) }



            // PASSWORD VALIDATION
            if (password.isEmpty()){
                etRPassword.error = "Password is Required"
                etRPassword.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError++
                return@setOnClickListener
            } else { etRPassword.setBackgroundResource(R.drawable.edit_text_rounded) }
            if (password.length < 6){
                etRPassword.error = "Password must contain at least 6 characters"
                etRPassword.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError++
                return@setOnClickListener
            } else { etRPassword.setBackgroundResource(R.drawable.edit_text_rounded) }
            if (password.contains(" ")){
                etRPassword.error = "Password must not contain any spaces"
                etRPassword.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError++
                return@setOnClickListener
            } else { etRPassword.setBackgroundResource(R.drawable.edit_text_rounded) }



            // CONFIRM PASSWORD VALIDATION
            if (confirmPassword.isEmpty()){
                etRConfirmPassword.error = "Confirm Password is Required"
                etRConfirmPassword.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError++
                return@setOnClickListener
            } else { etRConfirmPassword.setBackgroundResource(R.drawable.edit_text_rounded) }
            if (confirmPassword != password){
                etRConfirmPassword.error = "Confirm Password does not match Password"
                etRConfirmPassword.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError++
                return@setOnClickListener
            } else { etRConfirmPassword.setBackgroundResource(R.drawable.edit_text_rounded) }


            // CHECK ERRORS
            if(countError == 0) {
                val customer = RegisterCustomerModel(fullName, emailAddress, contactNumber, password)
                viewModel.registerNewCustomer(customer)
            }
        }
    }
}