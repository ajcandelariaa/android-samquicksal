package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.CurrentOrdersModel
import com.altwav.samquicksal2.models.OrderingFoodItemModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class CurrentOrdersViewModel: ViewModel() {
    var createCurrentOrdersResponseLD: MutableLiveData<List<CurrentOrdersModel>> =
        MutableLiveData()

    fun getCurrentOrdersObserver(): MutableLiveData<List<CurrentOrdersModel>> {
        return createCurrentOrdersResponseLD
    }

    fun getCurrentOrdersInfo(cust_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getCurrentOrders(cust_id)
        call.enqueue(object : retrofit2.Callback<List<CurrentOrdersModel>> {
            override fun onFailure(call: Call<List<CurrentOrdersModel>>?, t: Throwable?) {
                createCurrentOrdersResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<List<CurrentOrdersModel>>?,
                response: Response<List<CurrentOrdersModel>>?
            ) {
                if(response != null){
                    createCurrentOrdersResponseLD.postValue(response.body())
                } else {
                    createCurrentOrdersResponseLD.postValue(null)
                }
            }

        })
    }
}