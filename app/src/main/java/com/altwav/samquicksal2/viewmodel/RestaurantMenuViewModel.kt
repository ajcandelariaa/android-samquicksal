package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.RestaurantMenuModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class RestaurantMenuViewModel: ViewModel() {
    var createRestaurantMenuViewModelResponseLD: MutableLiveData<RestaurantMenuModelResponse> =
        MutableLiveData()

    fun getRestaurantMenuObserver(): MutableLiveData<RestaurantMenuModelResponse> {
        return createRestaurantMenuViewModelResponseLD
    }

    fun getMenuInfo(restaurant: Int, cust_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getRestaurantMenuInfo(restaurant, cust_id)
        call.enqueue(object : retrofit2.Callback<RestaurantMenuModelResponse> {
            override fun onFailure(call: Call<RestaurantMenuModelResponse>?, t: Throwable?) {
                createRestaurantMenuViewModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<RestaurantMenuModelResponse>?,
                response: Response<RestaurantMenuModelResponse>?
            ) {
                if(response != null){
                    createRestaurantMenuViewModelResponseLD.postValue(response.body())
                } else {
                    createRestaurantMenuViewModelResponseLD.postValue(null)
                }
            }

        })
    }
}