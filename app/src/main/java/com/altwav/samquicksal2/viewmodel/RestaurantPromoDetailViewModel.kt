package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.RestaurantPromoDetailModel
import com.altwav.samquicksal2.models.RestaurantPromoDetailModelResponse
import com.altwav.samquicksal2.models.RestaurantRewardPromoModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class RestaurantPromoDetailViewModel: ViewModel() {
    var createRestaurantPromoDetailResponseLD: MutableLiveData<RestaurantPromoDetailModelResponse> =
        MutableLiveData()

    fun getRestaurantPromoDetailObserver(): MutableLiveData<RestaurantPromoDetailModelResponse> {
        return createRestaurantPromoDetailResponseLD
    }

    fun getPromoDetailInfo(promoDetail: RestaurantPromoDetailModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getRestaurantPromoDetailInfo(promoDetail.promoId!!, promoDetail.restaurantId!!)
        call.enqueue(object : retrofit2.Callback<RestaurantPromoDetailModelResponse> {
            override fun onFailure(call: Call<RestaurantPromoDetailModelResponse>?, t: Throwable?) {
                createRestaurantPromoDetailResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<RestaurantPromoDetailModelResponse>?,
                response: Response<RestaurantPromoDetailModelResponse>?
            ) {
                if(response != null){
                    createRestaurantPromoDetailResponseLD.postValue(response.body())
                } else {
                    createRestaurantPromoDetailResponseLD.postValue(null)
                }
            }

        })
    }
}