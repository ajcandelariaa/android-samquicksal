package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.SubmitOrdersModel
import com.altwav.samquicksal2.models.UpdateFoodItemModel
import com.altwav.samquicksal2.models.UpdateFoodItemModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class SubmitOrdersViewModel: ViewModel() {
    var createSubmitOrdersResponseLD: MutableLiveData<SubmitOrdersModel> =
        MutableLiveData()

    fun getSubmitOrdersObserver(): MutableLiveData<SubmitOrdersModel> {
        return createSubmitOrdersResponseLD
    }

    fun getSubmitOrdersInfo(cust_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.submitCurrentOrders(cust_id)
        call.enqueue(object : retrofit2.Callback<SubmitOrdersModel> {
            override fun onFailure(call: Call<SubmitOrdersModel>?, t: Throwable?) {
                createSubmitOrdersResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<SubmitOrdersModel>?,
                response: Response<SubmitOrdersModel>?
            ) {
                if(response != null){
                    createSubmitOrdersResponseLD.postValue(response.body())
                } else {
                    createSubmitOrdersResponseLD.postValue(null)
                }
            }

        })
    }
}