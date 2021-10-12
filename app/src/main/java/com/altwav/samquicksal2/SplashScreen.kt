package com.altwav.samquicksal2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            val intent = Intent(this,  Login::class.java)
            startActivity(intent)
            finish()
        }, 1000)

    }
}