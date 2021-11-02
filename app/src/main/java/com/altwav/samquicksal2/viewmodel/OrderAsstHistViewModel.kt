package com.altwav.samquicksal2.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.OrderAsstHistModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class OrderAsstHistViewModel: ViewModel() {
    var createOrderAsstHistResponseLD: MutableLiveData<List<OrderAsstHistModel>> =
        MutableLiveData()

    fun getOrderAsstHistObserver(): MutableLiveData<List<OrderAsstHistModel>> {
        return createOrderAsstHistResponseLD
    }

    fun getOrderAsstHistInfo(cust_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.orderingAssistanceHistory(cust_id)
        call.enqueue(object : retrofit2.Callback<List<OrderAsstHistModel>> {
            override fun onFailure(call: Call<List<OrderAsstHistModel>>?, t: Throwable?) {
                createOrderAsstHistResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<List<OrderAsstHistModel>>?,
                response: Response<List<OrderAsstHistModel>>?
            ) {
                if(response != null){
                    createOrderAsstHistResponseLD.postValue(response.body())
                } else {
                    createOrderAsstHistResponseLD.postValue(null)
                }
            }

        })
    }
}