package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.AccountCustomerModelResponse
import com.altwav.samquicksal2.models.ChooseOrderSetModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class ChooseOrderSetViewModel:ViewModel() {
    var createChooseOrderSetModelResponseLD: MutableLiveData<ChooseOrderSetModelResponse> =
    MutableLiveData()

    fun getAccountCustomerObserver(): MutableLiveData<ChooseOrderSetModelResponse> {
        return createChooseOrderSetModelResponseLD
    }

    fun getAccountInfoCustomer(restaurantId: Int, customerId: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getRestaurantOrderSet(restaurantId, customerId)
        call.enqueue(object : retrofit2.Callback<ChooseOrderSetModelResponse> {
            override fun onFailure(call: Call<ChooseOrderSetModelResponse>?, t: Throwable?) {
                createChooseOrderSetModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<ChooseOrderSetModelResponse>?,
                response: Response<ChooseOrderSetModelResponse>?
            ) {
                if(response != null){
                    createChooseOrderSetModelResponseLD.postValue(response.body())
                } else {
                    createChooseOrderSetModelResponseLD.postValue(null)
                }
            }

        })
    }
}