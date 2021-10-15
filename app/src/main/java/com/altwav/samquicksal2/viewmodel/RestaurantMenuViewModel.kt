package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.RestaurantMenuModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class RestaurantMenuViewModel: ViewModel() {
    var createRestaurantMenuViewModelResponseLD: MutableLiveData<List<RestaurantMenuModelResponse>> =
        MutableLiveData()

    fun getRestaurantMenuObserver(): MutableLiveData<List<RestaurantMenuModelResponse>> {
        return createRestaurantMenuViewModelResponseLD
    }

    fun getMenuInfo(restaurant: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getRestaurantMenuInfo(restaurant)
        call.enqueue(object : retrofit2.Callback<List<RestaurantMenuModelResponse>> {
            override fun onFailure(call: Call<List<RestaurantMenuModelResponse>>?, t: Throwable?) {
                createRestaurantMenuViewModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<List<RestaurantMenuModelResponse>>?,
                response: Response<List<RestaurantMenuModelResponse>>?
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