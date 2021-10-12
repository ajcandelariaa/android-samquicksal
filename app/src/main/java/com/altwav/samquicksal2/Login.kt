package com.altwav.samquicksal2

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvSignup.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        tvIncorretPassword.visibility = View.GONE

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
                Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }


        }
    }
}