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

    @GET("get-restaurants/rewards/{id}")
    fun getRestaurantRewardPromoInfo(@Path("id") id: Int): Call<RestaurantRewardPromoModelResponse>

    @GET("get-restaurants/menu/{id}")
    fun getRestaurantMenuInfo(@Path("id") id: Int): Call<List<RestaurantMenuModelResponse>>

    @GET("get-restaurants/promo/detail/{promoId}/{restaurantId}")
    fun getRestaurantPromoDetailInfo(@Path("promoId") promoId: Int, @Path("restaurantId") restaurantId:Int): Call<RestaurantPromoDetailModelResponse>

    @GET("promos")
    fun getPromosList(): Call<List<ListOfPromosModel>>

    @GET("get-restaurants/choose-order-set/{id}/{custId}")
    fun getRestaurantOrderSet(@Path("id") id: Int, @Path("custId") custId:Int): Call<ChooseOrderSetModelResponse>

    @GET("get-notifications/{id}")
    fun getNotificationsList(@Path("id") id: Int): Call<List<NotificationListModelResponse>>



    @POST("register-customer")
    fun registerCustomer(@Body params: RegisterCustomerModel): Call<RegisterCustomerModelResponse>

    @POST("login-customer")
    fun loginCustomer(@Body params: LoginCustomerModel): Call<LoginCustomerModelResponse>

    @POST("submit-queue-form")
    fun submitQueueForm(@Body params: SubmitQueueFormModel): Call<SubmitQueueFormModelResponse>
}