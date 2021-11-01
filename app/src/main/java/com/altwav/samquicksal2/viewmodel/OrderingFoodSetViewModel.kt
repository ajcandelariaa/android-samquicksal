package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.OrderingFoodSetModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class OrderingFoodSetViewModel: ViewModel() {
    var createOrderFSModelResponseLD: MutableLiveData<OrderingFoodSetModelResponse> =
        MutableLiveData()

    fun getOrderFSObserver(): MutableLiveData<OrderingFoodSetModelResponse> {
        return createOrderFSModelResponseLD
    }

    fun getOrderFSInfo(cust_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.orderingFoodSet(cust_id)
        call.enqueue(object : retrofit2.Callback<OrderingFoodSetModelResponse> {
            override fun onFailure(call: Call<OrderingFoodSetModelResponse>?, t: Throwable?) {
                createOrderFSModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<OrderingFoodSetModelResponse>?,
                response: Response<OrderingFoodSetModelResponse>?
            ) {
                if(response != null){
                    createOrderFSModelResponseLD.postValue(response.body())
                } else {
                    createOrderFSModelResponseLD.postValue(null)
                }
            }

        })
    }
}