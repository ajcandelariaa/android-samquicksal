package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.OrderingFoodItemModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class OrderingFoodItemViewModel: ViewModel() {
    var createOrderFIModelResponseLD: MutableLiveData<List<OrderingFoodItemModelResponse>> =
        MutableLiveData()

    fun getOrderFIObserver(): MutableLiveData<List<OrderingFoodItemModelResponse>> {
        return createOrderFIModelResponseLD
    }

    fun getOrderFIInfo(restAcc_id: Int, orderSet_id: Int, foodSet_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.orderingFoodItem(restAcc_id, orderSet_id, foodSet_id)
        call.enqueue(object : retrofit2.Callback<List<OrderingFoodItemModelResponse>> {
            override fun onFailure(call: Call<List<OrderingFoodItemModelResponse>>?, t: Throwable?) {
                createOrderFIModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<List<OrderingFoodItemModelResponse>>?,
                response: Response<List<OrderingFoodItemModelResponse>>?
            ) {
                if(response != null){
                    createOrderFIModelResponseLD.postValue(response.body())
                } else {
                    createOrderFIModelResponseLD.postValue(null)
                }
            }

        })
    }
}