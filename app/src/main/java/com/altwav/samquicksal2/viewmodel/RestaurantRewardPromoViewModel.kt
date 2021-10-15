package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.RestaurantRewardPromoModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class RestaurantRewardPromoViewModel: ViewModel() {
    var createRestaurantRewardPromoModelResponseLD: MutableLiveData<RestaurantRewardPromoModelResponse> =
        MutableLiveData()

    fun getRestaurantRewardPromoObserver(): MutableLiveData<RestaurantRewardPromoModelResponse> {
        return createRestaurantRewardPromoModelResponseLD
    }

    fun getRewardPromoInfo(customer: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getRestaurantRewardPromoInfo(customer)
        call.enqueue(object : retrofit2.Callback<RestaurantRewardPromoModelResponse> {
            override fun onFailure(call: Call<RestaurantRewardPromoModelResponse>?, t: Throwable?) {
                createRestaurantRewardPromoModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<RestaurantRewardPromoModelResponse>?,
                response: Response<RestaurantRewardPromoModelResponse>?
            ) {
                if(response != null){
                    createRestaurantRewardPromoModelResponseLD.postValue(response.body())
                } else {
                    createRestaurantRewardPromoModelResponseLD.postValue(null)
                }
            }

        })
    }
}