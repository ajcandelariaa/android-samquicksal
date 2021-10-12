package com.altwav.samquicksal2.retrofit

import com.altwav.samquicksal2.models.*
import retrofit2.Call
import retrofit2.http.*

interface RetroServiceInterface {

    @GET("restaurants")
    fun getRestaurantList(): Call<List<ListOfRestaurantModel>>

    @GET("get-homepage-info/{id}")
    fun getHomepageCustomerInfo(@Path("id") id: Int): Call<HomepageCustomerModelResponse>

    @GET("get-account-info/{id}")
    fun getAccountCustomerInfo(@Path("id") id: Int): Call<AccountCustomerModelResponse>

    @GET("get-restaurants/about/{id}")
    fun getRestaurantAboutInfo(@Path("id") id: Int): Call<RestaurantModelResponse>




    @POST("register-customer")
    fun registerCustomer(@Body params: RegisterCustomerModel): Call<RegisterCustomerModelResponse>

    @POST("login-customer")
    fun loginCustomer(@Body params: LoginCustomerModel): Call<LoginCustomerModelResponse>
}