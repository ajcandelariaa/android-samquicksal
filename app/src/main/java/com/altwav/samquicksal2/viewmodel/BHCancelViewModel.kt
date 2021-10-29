package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.BHCancelledModel
import com.altwav.samquicksal2.models.BHCancelledModelResponse
import com.altwav.samquicksal2.models.CancelBookingModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class BHCancelViewModel: ViewModel() {
    var createBHCancelledModelResponseLD: MutableLiveData<BHCancelledModelResponse> =
        MutableLiveData()

    fun getBHCancelledObserver(): MutableLiveData<BHCancelledModelResponse> {
        return createBHCancelledModelResponseLD
    }

    fun getBHCancelledInfo(customer: BHCancelledModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.bookingHistoryCancelled(customer)
        call.enqueue(object : retrofit2.Callback<BHCancelledModelResponse> {
            override fun onFailure(call: Call<BHCancelledModelResponse>?, t: Throwable?) {
                createBHCancelledModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<BHCancelledModelResponse>?,
                response: Response<BHCancelledModelResponse>?
            ) {
                if(response != null){
                    createBHCancelledModelResponseLD.postValue(response.body())
                } else {
                    createBHCancelledModelResponseLD.postValue(null)
                }
            }

        })
    }
}