package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.AddFooditemModel
import com.altwav.samquicksal2.models.AddFooditemModelResponse
import com.altwav.samquicksal2.models.UpdateFoodItemModel
import com.altwav.samquicksal2.models.UpdateFoodItemModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class UpdateFoodItemViewModel: ViewModel() {
    var createUpdateOrderingFIResponseLD: MutableLiveData<UpdateFoodItemModelResponse> =
        MutableLiveData()

    fun getUpdateOrderingFIObserver(): MutableLiveData<UpdateFoodItemModelResponse> {
        return createUpdateOrderingFIResponseLD
    }

    fun getUpdateOrderingFIInfo(foodItem: UpdateFoodItemModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.orderingUpdateFoodItem(foodItem)
        call.enqueue(object : retrofit2.Callback<UpdateFoodItemModelResponse> {
            override fun onFailure(call: Call<UpdateFoodItemModelResponse>?, t: Throwable?) {
                createUpdateOrderingFIResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<UpdateFoodItemModelResponse>?,
                response: Response<UpdateFoodItemModelResponse>?
            ) {
                if(response != null){
                    createUpdateOrderingFIResponseLD.postValue(response.body())
                } else {
                    createUpdateOrderingFIResponseLD.postValue(null)
                }
            }

        })
    }
}