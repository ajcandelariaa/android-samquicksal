package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.GeofencingModel
import com.altwav.samquicksal2.models.GeofencingModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class GeofencingViewModel: ViewModel() {
    var createGeofencingResponseLD: MutableLiveData<GeofencingModelResponse> =
        MutableLiveData()

    fun getGeofencingObserver(): MutableLiveData<GeofencingModelResponse> {
        return createGeofencingResponseLD
    }

    fun getGeofencingInfo(customer: GeofencingModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.geofencingListener(customer)
        call.enqueue(object : retrofit2.Callback<GeofencingModelResponse> {
            override fun onFailure(call: Call<GeofencingModelResponse>?, t: Throwable?) {
                createGeofencingResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<GeofencingModelResponse>?,
                response: Response<GeofencingModelResponse>?
            ) {
                if(response != null){
                    createGeofencingResponseLD.postValue(response.body())
                } else {
                    createGeofencingResponseLD.postValue(null)
                }
            }

        })
    }
}