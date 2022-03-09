package com.altwav.samquicksal2.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {
    companion object {
//        const val BASE_URL = "http://192.168.1.24:8000/api/customer/"
        const val BASE_URL = "https://www.samquicksal.com/api/customer/"
        fun getRetroInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}