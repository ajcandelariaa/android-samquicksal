package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.OrderingAssistanceModel
import com.altwav.samquicksal2.models.OrderingAssistanceModelResponse
import com.altwav.samquicksal2.models.UpdateFoodItemModel
import com.altwav.samquicksal2.models.UpdateFoodItemModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class OrderingAssistanceViewModel: ViewModel() {
    var createOrderingAssistanceResponseLD: MutableLiveData<OrderingAssistanceModelResponse> =
        MutableLiveData()

    fun getOrderingAssistanceObserver(): MutableLiveData<OrderingAssistanceModelResponse> {
        return createOrderingAssistanceResponseLD
    }

    fun getOrderingAssistanceInfo(assistance: OrderingAssistanceModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.orderingAssistance(assistance)
        call.enqueue(object : retrofit2.Callback<OrderingAssistanceModelResponse> {
            override fun onFailure(call: Call<OrderingAssistanceModelResponse>?, t: Throwable?) {
                createOrderingAssistanceResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<OrderingAssistanceModelResponse>?,
                response: Response<OrderingAssistanceModelResponse>?
            ) {
                if(response != null){
                    createOrderingAssistanceResponseLD.postValue(response.body())
                } else {
                    createOrderingAssistanceResponseLD.postValue(null)
                }
            }

        })
    }
}