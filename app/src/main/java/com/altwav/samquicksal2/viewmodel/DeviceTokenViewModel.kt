package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.DeviceTokenModel
import com.altwav.samquicksal2.models.DeviceTokenModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class DeviceTokenViewModel: ViewModel() {
    var createDeviceTokenModelResponseLD: MutableLiveData<DeviceTokenModelResponse> =
        MutableLiveData()

    fun getDeviceTokenObserver(): MutableLiveData<DeviceTokenModelResponse> {
        return createDeviceTokenModelResponseLD
    }

    fun getDeviceTokenInfo(customer: DeviceTokenModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.updateDeviceToken(customer)
        call.enqueue(object : retrofit2.Callback<DeviceTokenModelResponse> {
            override fun onFailure(call: Call<DeviceTokenModelResponse>?, t: Throwable?) {
                createDeviceTokenModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<DeviceTokenModelResponse>?,
                response: Response<DeviceTokenModelResponse>?
            ) {
                if(response != null){
                    createDeviceTokenModelResponseLD.postValue(response.body())
                } else {
                    createDeviceTokenModelResponseLD.postValue(null)
                }
            }

        })
    }
}