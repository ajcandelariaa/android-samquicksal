package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.CancelBookingModelResponse
import com.altwav.samquicksal2.models.RestaurantModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class CancelBookingViewModel:ViewModel() {
    var createCancelBookModelResponseLD: MutableLiveData<CancelBookingModelResponse> =
        MutableLiveData()

    fun getCancelBookObserver(): MutableLiveData<CancelBookingModelResponse> {
        return createCancelBookModelResponseLD
    }

    fun getCancelBookInfo(customer: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.cancelBooking(customer)
        call.enqueue(object : retrofit2.Callback<CancelBookingModelResponse> {
            override fun onFailure(call: Call<CancelBookingModelResponse>?, t: Throwable?) {
                createCancelBookModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<CancelBookingModelResponse>?,
                response: Response<CancelBookingModelResponse>?
            ) {
                if(response != null){
                    createCancelBookModelResponseLD.postValue(response.body())
                } else {
                    createCancelBookModelResponseLD.postValue(null)
                }
            }

        })
    }
}