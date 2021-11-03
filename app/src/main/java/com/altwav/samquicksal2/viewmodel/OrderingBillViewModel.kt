package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.OrderingBillModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class OrderingBillViewModel: ViewModel() {
    var createOrderingBillResponseLD: MutableLiveData<OrderingBillModel> =
        MutableLiveData()

    fun getOrderingBillObserver(): MutableLiveData<OrderingBillModel> {
        return createOrderingBillResponseLD
    }

    fun getOrderingBillInfo(cust_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.orderingBill(cust_id)
        call.enqueue(object : retrofit2.Callback<OrderingBillModel> {
            override fun onFailure(call: Call<OrderingBillModel>?, t: Throwable?) {
                createOrderingBillResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<OrderingBillModel>?,
                response: Response<OrderingBillModel>?
            ) {
                if(response != null){
                    createOrderingBillResponseLD.postValue(response.body())
                } else {
                    createOrderingBillResponseLD.postValue(null)
                }
            }

        })
    }
}