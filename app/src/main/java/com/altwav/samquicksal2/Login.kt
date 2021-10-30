package com.altwav.samquicksal2

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.models.LoginCustomerModel
import com.altwav.samquicksal2.models.LoginCustomerModelResponse
import com.altwav.samquicksal2.viewmodel.LoginCustomerViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
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

        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        loadData()
        checkIfLoggedIn()
        getLogin()

    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setIcon(R.mipmap.ic_launcher)
            .setMessage("Are you sure you want to exit?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                finish()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.cancel()
            }
            .show()
    }

    private fun loadData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedCustomerId = sharedPreferences.getInt("CUSTOMER_ID", 0)

        if (savedCustomerId != 0){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkIfLoggedIn(){
        viewModel = ViewModelProvider(this).get(LoginCustomerViewModel::class.java)
        viewModel.getLoginCustomerObserver().observe(this, Observer <LoginCustomerModelResponse>{
            if(it == null){
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            } else {
                if(it.status == "Login Successfully"){
                    Toast.makeText(this, "${it.status}", Toast.LENGTH_SHORT).show()

                    //SAVE CUSTOMER ID TO SHARED PREFERENCES
                    val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.apply {
                        putInt("CUSTOMER_ID", it.id!!)
                    }.apply()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "${it.status}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getLogin(){
        btnLogin.setOnClickListener {
            val emailAddress = etLEmailAddress.text.toString()
            val password = etLPassword.text.toString()
            var countError: Int = 0

            // EMAIL ADDRESS VALIDATION
            if (emailAddress.isEmpty()){
                etLEmailAddress.error = "Email Address is Required"
                etLEmailAddress.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError += 1
                return@setOnClickListener
            } else { etLEmailAddress.setBackgroundResource(R.drawable.edit_text_rounded) }

            // PASSWORD VALIDATION
            if (password.isEmpty()){
                etLPassword.error = "Password is Required"
                etLPassword.setBackgroundResource(R.drawable.edit_text_rounded_error)
                countError += 1
                return@setOnClickListener
            } else { etLPassword.setBackgroundResource(R.drawable.edit_text_rounded) }


            // CHECK ERRORS
            if(countError == 0) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }

                    val token = task.result
                    val customer = LoginCustomerModel(emailAddress, password, token)
                    viewModel.loginCustomer(customer)
                })

            }
        }
    }
}