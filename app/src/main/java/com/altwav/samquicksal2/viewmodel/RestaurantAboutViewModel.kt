package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.AccountCustomerModelResponse
import com.altwav.samquicksal2.models.RestaurantModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class RestaurantAboutViewModel:ViewModel() {
    var createRestaurantAboutModelResponseLD: MutableLiveData<RestaurantModelResponse> =
        MutableLiveData()

    fun getRestaurantAboutObserver(): MutableLiveData<RestaurantModelResponse> {
        return createRestaurantAboutModelResponseLD
    }

    fun getRestaurantAboutInfo(customer: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getRestaurantAboutInfo(customer)
        call.enqueue(object : retrofit2.Callback<RestaurantModelResponse> {
            override fun onFailure(call: Call<RestaurantModelResponse>?, t: Throwable?) {
                createRestaurantAboutModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<RestaurantModelResponse>?,
                response: Response<RestaurantModelResponse>?
            ) {
                if(response != null){
                    createRestaurantAboutModelResponseLD.postValue(response.body())
                } else {
                    createRestaurantAboutModelResponseLD.postValue(null)
                }
            }

        })
    }
}