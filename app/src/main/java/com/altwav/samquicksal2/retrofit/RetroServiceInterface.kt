package com.altwav.samquicksal2.retrofit

import android.content.Context
import com.altwav.samquicksal2.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @GET("get-restaurants/menu/{id}/{cust_id}")
    fun getRestaurantMenuInfo(@Path("id") id: Int, @Path("cust_id") cust_id: Int): Call<RestaurantMenuModelResponse>

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

    @GET("get-notifications/completed/{cust_id}/{notif_id}")
    fun notifCompleted(@Path("cust_id") cust_id: Int, @Path("notif_id") notif_id:Int): Call<NotifCompletedModel>

    @GET("get-notifications/qr-validate/{cust_id}/{notif_id}")
    fun notifQrValidate(@Path("cust_id") cust_id: Int, @Path("notif_id") notif_id:Int): Call<NotifQrValidateModel>

    @GET("get-notifications/qr-approved/{cust_id}/{notif_id}")
    fun notifQrApproved(@Path("cust_id") cust_id: Int, @Path("notif_id") notif_id:Int): Call<NotifQrAppModel>

    @GET("get-notifications/geofencing/{cust_id}/{notif_id}")
    fun notifGeofencing(@Path("cust_id") cust_id: Int, @Path("notif_id") notif_id:Int): Call<NotifGeofencingModel>




    @GET("get-booking-history/{cust_id}")
    fun bookingHistory(@Path("cust_id") id: Int): Call<List<BookingHistoryModelResponse>>

    @GET("ordering/food-set/{cust_id}")
    fun orderingFoodSet(@Path("cust_id") id: Int): Call<OrderingFoodSetModelResponse>

    @GET("ordering/food-item/{restAcc_id}/{orderSet_id}/{foodSet_id}")
    fun orderingFoodItem(@Path("restAcc_id") restAcc_id: Int, @Path("orderSet_id") orderSet_id: Int, @Path("foodSet_id") foodSet_id: Int): Call<List<OrderingFoodItemModelResponse>>

    @GET("ordering/orders/{cust_id}")
    fun getCurrentOrders(@Path("cust_id") cust_id: Int): Call<List<CurrentOrdersModel>>

    @GET("ordering/orders/submit/{cust_id}")
    fun submitCurrentOrders(@Path("cust_id") cust_id: Int): Call<SubmitOrdersModel>

    @GET("ordering/assistance/history/{cust_id}")
    fun orderingAssistanceHistory(@Path("cust_id") cust_id: Int): Call<List<OrderAsstHistModel>>

    @GET("ordering/bill/{cust_id}")
    fun orderingBill(@Path("cust_id") cust_id: Int): Call<OrderingBillModel>

    @GET("ordering/checkout/status/{cust_id}")
    fun checkoutStatus(@Path("cust_id") cust_id: Int): Call<CheckoutStatusModel>

    @GET("ordering/checkout/rating-feedback/{cust_id}")
    fun ratingFeedback(@Path("cust_id") cust_id: Int): Call<RFStatusModel>

    @GET("ordering/gcash/status/{cust_id}")
    fun gCashCheckoutStatus(@Path("cust_id") cust_id: Int): Call<GCashCStatusModel>

    @GET("get-stamp-cards/{cust_id}")
    fun stampCards(@Path("cust_id") cust_id: Int): Call<List<StampCardsModel>>

    @GET("get-stamp-cards/details/{stamp_id}")
    fun stampCardDetails(@Path("stamp_id") stamp_id: Int): Call<StampDetailsModel>

    @GET("get-restaurants/reviews/{rest_id}")
    fun getRestaurantReviews(@Path("rest_id") rest_id: Int): Call<RestoReviewModel>

    @GET("rated-restaurants")
    fun getRatedRestaurants(): Call<List<RatedRestaurantsModel>>

    @GET("get-restaurants/get-date-time/{rest_id}")
    fun getReservationDT(@Path("rest_id") rest_id: Int): Call<ReservationDTModel>

    @GET("scan-qr/{cust_id}/{request_cust_id}")
    fun scanQRCode(@Path("cust_id") cust_id: Int, @Path("request_cust_id") request_cust_id:Int): Call<QRScannedModel>

    @GET("ordering/request-access/{cust_id}/{request_cust_id}")
    fun orderingReqAccess(@Path("cust_id") cust_id: Int, @Path("request_cust_id") request_cust_id:Int): Call<QrReqAccessModel>

    @GET("ordering/check-customer-access/food-set/{cust_id}")
    fun ordrCheckCusAccess(@Path("cust_id") cust_id: Int) : Call<OrdrChkCusAccsModel>






    // POST METHODS
    @POST("register-customer")
    fun registerCustomer(@Body params: RegisterCustomerModel): Call<RegisterCustomerModelResponse>

    @POST("login-customer")
    fun loginCustomer(@Body params: LoginCustomerModel): Call<LoginCustomerModelResponse>

    @POST("submit-queue-form")
    fun submitQueueForm(@Body params: SubmitQueueFormModel): Call<SubmitQueueFormModelResponse>

    @POST("submit-reserve-form")
    fun submitReserveForm(@Body params: SubmitReserveFormModel): Call<SubmitReserveFormModelResponse>

    @POST("forgot-password")
    fun forgotPassword(@Body params: ForgotPasswordModel): Call<ForgotPasswordModelResponse>

    @POST("update-device-token")
    fun updateDeviceToken(@Body params: DeviceTokenModel): Call<DeviceTokenModelResponse>

    @POST("ordering/checkout/rating-feedback/submit")
    fun ratingFBSubmit(@Body params: RFSubmitFormModel): Call<RFSubmitFormModelResponse>

    @POST("ordering/assistance")
    fun orderingAssistance(@Body params: OrderingAssistanceModel): Call<OrderingAssistanceModelResponse>

    @POST("ordering/food-item/add")
    fun orderingAddFoodItem(@Body params: AddFooditemModel): Call<AddFooditemModelResponse>

    @POST("ordering/food-item/update")
    fun orderingUpdateFoodItem(@Body params: UpdateFoodItemModel): Call<UpdateFoodItemModelResponse>

    @POST("get-booking-history/cancelled")
    fun bookingHistoryCancelled(@Body params: BHCancelledModel): Call<BHCancelledModelResponse>

    @POST("get-booking-history/complete")
    fun bookingHistoryComplete(@Body params: BHCompleteModel): Call<BHCompleteModelResponse>


    @POST("ordering/checkout")
    fun orderingCheckout(@Body params: OrderingAssistanceModel): Call<OrderingAssistanceModelResponse>

    @Multipart
    @POST("ordering/checkout/gcash-upload-receipt")
    fun gcashUploadImage(
        @Part gCashReceipt: MultipartBody.Part,
        @Part("cust_id") cust_id: RequestBody,
        @Part("book_id") book_id: RequestBody,
        @Part("book_type") book_type: RequestBody,
    ): Call<UploadReceiptModelResponse>

    @POST("geofencing/notification")
    fun geofencingListener(): Call<GeofencingModelResponse>
//    fun geofencingListener(@Body params: GeofencingModel): Call<GeofencingModelResponse>



    @POST("nearby-restaurants")
    fun getNearbyResto(@Body params: NearbyRestoModel): Call<List<NearbyRestoModelResponse>>

    @POST("ordering/request-access/approved-declined")
    fun qrReqAppDec(@Body params: QrReqAppDecModel): Call<QrReqAppDecModelResponse>

}