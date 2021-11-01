package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.AddFooditemModel
import com.altwav.samquicksal2.models.AddFooditemModelResponse
import com.altwav.samquicksal2.models.NotifRunawayModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class AddFoodItemViewModel: ViewModel() {
    var createAddOrderingFIResponseLD: MutableLiveData<AddFooditemModelResponse> =
        MutableLiveData()

    fun getAddOrderingFIObserver(): MutableLiveData<AddFooditemModelResponse> {
        return createAddOrderingFIResponseLD
    }

    fun getAddOrderingFIInfo(foodItem: AddFooditemModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.orderingAddFoodItem(foodItem)
        call.enqueue(object : retrofit2.Callback<AddFooditemModelResponse> {
            override fun onFailure(call: Call<AddFooditemModelResponse>?, t: Throwable?) {
                createAddOrderingFIResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<AddFooditemModelResponse>?,
                response: Response<AddFooditemModelResponse>?
            ) {
                if(response != null){
                    createAddOrderingFIResponseLD.postValue(response.body())
                } else {
                    createAddOrderingFIResponseLD.postValue(null)
                }
            }

        })
    }
}