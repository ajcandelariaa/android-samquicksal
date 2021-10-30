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

    @GET("get-live-status/{id}")
    fun getLiveStatus(@Path("id") id: Int): Call<LiveStatusModelResponse>

    @GET("cancel-booking/{id}")
    fun cancelBooking(@Path("id") id: Int): Call<CancelBookingModelResponse>



    // NOTIFICATIONS RESPONSE
    @GET("get-notifications/pending/{cust_id}/{notif_id}")
    fun notifPending(@Path("cust_id") cust_id: Int, @Path("notif_id") notif_id:Int): Call<NotifPendingModel>

    @GET("get-notifications/cancelled/{cust_id}/{notif_id}")
    fun notifCancelled(@Path("cust_id") cust_id: Int, @Path("notif_id") notif_id:Int): Call<NotifCancelledModel>

    @GET("get-notifications/declined/{cust_id}/{notif_id}")
    fun notifDeclined(@Path("cust_id") cust_id: Int, @Path("notif_id") notif_id:Int): Call<NotifDeclinedModel>

    @GET("get-notifications/approved/{cust_id}/{notif_id}")
    fun notifApproved(@Path("cust_id") cust_id: Int, @Path("notif_id") notif_id:Int): Call<NotifApprovedModel>

    @GET("get-notifications/no-show/{cust_id}/{notif_id}")
    fun notifNoShow(@Path("cust_id") cust_id: Int, @Path("notif_id") notif_id:Int): Call<NotifNoShowModel>

    @GET("get-notifications/runaway/{cust_id}/{notif_id}")
    fun notifRunaway(@Path("cust_id") cust_id: Int, @Path("notif_id") notif_id:Int): Call<NotifRunawayModel>



    @GET("get-booking-history/{cust_id}")
    fun bookingHistory(@Path("cust_id") id: Int): Call<List<BookingHistoryModelResponse>>

    @POST("get-booking-history/cancelled")
    fun bookingHistoryCancelled(@Body params: BHCancelledModel): Call<BHCancelledModelResponse>






    // POST METHODS
    @POST("register-customer")
    fun registerCustomer(@Body params: RegisterCustomerModel): Call<RegisterCustomerModelResponse>

    @POST("login-customer")
    fun loginCustomer(@Body params: LoginCustomerModel): Call<LoginCustomerModelResponse>

    @POST("submit-queue-form")
    fun submitQueueForm(@Body params: SubmitQueueFormModel): Call<SubmitQueueFormModelResponse>

    @POST("forgot-password")
    fun forgotPassword(@Body params: ForgotPasswordModel): Call<ForgotPasswordModelResponse>

    @POST("update-device-token")
    fun updateDeviceToken(@Body params: DeviceTokenModel): Call<DeviceTokenModelResponse>
}