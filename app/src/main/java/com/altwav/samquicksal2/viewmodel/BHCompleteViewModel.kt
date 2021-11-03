package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.BHCompleteModel
import com.altwav.samquicksal2.models.BHCompleteModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class BHCompleteViewModel: ViewModel() {
    var createBHCompleteModelResponseLD: MutableLiveData<BHCompleteModelResponse> =
        MutableLiveData()

    fun getBHCompleteObserver(): MutableLiveData<BHCompleteModelResponse> {
        return createBHCompleteModelResponseLD
    }

    fun getBHCompleteInfo(customer: BHCompleteModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.bookingHistoryComplete(customer)
        call.enqueue(object : retrofit2.Callback<BHCompleteModelResponse> {
            override fun onFailure(call: Call<BHCompleteModelResponse>?, t: Throwable?) {
                createBHCompleteModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<BHCompleteModelResponse>?,
                response: Response<BHCompleteModelResponse>?
            ) {
                if(response != null){
                    createBHCompleteModelResponseLD.postValue(response.body())
                } else {
                    createBHCompleteModelResponseLD.postValue(null)
                }
            }

        })
    }
}