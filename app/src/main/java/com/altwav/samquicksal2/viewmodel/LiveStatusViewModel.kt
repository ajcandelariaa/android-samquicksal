package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.LiveStatusModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class LiveStatusViewModel:ViewModel() {
    var createLiveStatusModelResponseLD: MutableLiveData<LiveStatusModelResponse> =
        MutableLiveData()

    fun getLiveStatusObserver(): MutableLiveData<LiveStatusModelResponse> {
        return createLiveStatusModelResponseLD
    }

    fun getLiveStatusInfo(customer: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getLiveStatus(customer)
        call.enqueue(object : retrofit2.Callback<LiveStatusModelResponse> {
            override fun onFailure(call: Call<LiveStatusModelResponse>?, t: Throwable?) {
                createLiveStatusModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<LiveStatusModelResponse>?,
                response: Response<LiveStatusModelResponse>?
            ) {
                if(response != null){
                    createLiveStatusModelResponseLD.postValue(response.body())
                } else {
                    createLiveStatusModelResponseLD.postValue(null)
                }
            }

        })
    }
}