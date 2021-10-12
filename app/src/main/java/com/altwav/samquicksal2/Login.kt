package com.altwav.samquicksal2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.models.LoginCustomerModel
import com.altwav.samquicksal2.models.LoginCustomerModelResponse
import com.altwav.samquicksal2.models.RegisterCustomerModel
import com.altwav.samquicksal2.viewmodel.LoginCustomerViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


class Login : AppCompatActivity() {
    private lateinit var viewModel: LoginCustomerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvSignup.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this).get(LoginCustomerViewModel::class.java)
        viewModel.getLoginCustomerObserver().observe(this, Observer <LoginCustomerModelResponse>{
            if(it == null){
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            } else {
                if(it.status == "Login Successfully"){
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

        btnLogin.setOnClickListener {
            val emailAddress = etLEmailAddress.text.toString()
            val password = etLPassword.text.toString()
            var countError: Int = 0


            // EMAIL ADDRESS VALIDATION
            if (emailAddress.isEmpty()){
                etLEmailAddress.error = "Email Address is Required"
                etLEmailAddress.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError++
                return@setOnClickListener
            } else { etLEmailAddress.setBackgroundResource(R.drawable.edit_text_rounded) }


            // PASSWORD VALIDATION
            if (password.isEmpty()){
                etLPassword.error = "Password is Required"
                etLPassword.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError++
                return@setOnClickListener
            } else { etLPassword.setBackgroundResource(R.drawable.edit_text_rounded) }


            // CHECK ERRORS
            if(countError == 0) {
                val customer = LoginCustomerModel(emailAddress, password)
                viewModel.loginCustomer(customer)
            }


        }
    }
}