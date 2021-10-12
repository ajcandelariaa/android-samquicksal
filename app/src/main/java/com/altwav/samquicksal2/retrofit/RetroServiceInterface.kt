package com.altwav.samquicksal2.retrofit

import com.altwav.samquicksal2.models.ListOfRestaurantModel
import retrofit2.Call
import retrofit2.http.GET

interface RetroServiceInterface {

    @GET("restaurants")
    fun getRestaurantList(): Call<List<ListOfRestaurantModel>>
}