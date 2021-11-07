package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.UpdateFoodItemModel
import com.altwav.samquicksal2.models.UpdateFoodItemModelResponse
import com.altwav.samquicksal2.models.UploadReceiptModel
import com.altwav.samquicksal2.models.UploadReceiptModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class UploadReceiptViewModell: ViewModel() {
    var createUploadReceiptResponseLD: MutableLiveData<UploadReceiptModelResponse> =
        MutableLiveData()

    fun getUploadReceiptObserver(): MutableLiveData<UploadReceiptModelResponse> {
        return createUploadReceiptResponseLD
    }

    fun getUploadReceiptInfo(gcashReceipt: String, cust_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.gcashUploadImage(gcashReceipt, cust_id)
        call.enqueue(object : retrofit2.Callback<UploadReceiptModelResponse> {
            override fun onFailure(call: Call<UploadReceiptModelResponse>?, t: Throwable?) {
                createUploadReceiptResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<UploadReceiptModelResponse>?,
                response: Response<UploadReceiptModelResponse>?
            ) {
                if(response != null){
                    createUploadReceiptResponseLD.postValue(response.body())
                } else {
                    createUploadReceiptResponseLD.postValue(null)
                }
            }

        })
    }
}