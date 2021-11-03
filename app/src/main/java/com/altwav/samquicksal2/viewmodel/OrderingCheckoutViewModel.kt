package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.OrderingAssistanceModel
import com.altwav.samquicksal2.models.OrderingAssistanceModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class OrderingCheckoutViewModel: ViewModel() {
    var createOrderingCheckoutResponseLD: MutableLiveData<OrderingAssistanceModelResponse> =
        MutableLiveData()

    fun getOrderingCheckoutObserver(): MutableLiveData<OrderingAssistanceModelResponse> {
        return createOrderingCheckoutResponseLD
    }

    fun getOrderingCheckoutInfo(assistance: OrderingAssistanceModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.orderingCheckout(assistance)
        call.enqueue(object : retrofit2.Callback<OrderingAssistanceModelResponse> {
            override fun onFailure(call: Call<OrderingAssistanceModelResponse>?, t: Throwable?) {
                createOrderingCheckoutResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<OrderingAssistanceModelResponse>?,
                response: Response<OrderingAssistanceModelResponse>?
            ) {
                if(response != null){
                    createOrderingCheckoutResponseLD.postValue(response.body())
                } else {
                    createOrderingCheckoutResponseLD.postValue(null)
                }
            }

        })
    }
}