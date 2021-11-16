package com.altwav.samquicksal2.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.UpdateFoodItemModel
import com.altwav.samquicksal2.models.UpdateFoodItemModelResponse
import com.altwav.samquicksal2.models.UploadReceiptModel
import com.altwav.samquicksal2.models.UploadReceiptModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class UploadReceiptViewModell: ViewModel() {
    var createUploadReceiptResponseLD: MutableLiveData<UploadReceiptModelResponse> =
        MutableLiveData()

    fun getUploadReceiptObserver(): MutableLiveData<UploadReceiptModelResponse> {
        return createUploadReceiptResponseLD
    }

    fun getUploadReceiptInfo(gCashReceipt: MultipartBody.Part, cust_id: RequestBody, book_id2: RequestBody, book_type2: RequestBody){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.gcashUploadImage(gCashReceipt, cust_id, book_id2, book_type2)
        call.enqueue(object : retrofit2.Callback<UploadReceiptModelResponse> {
            override fun onFailure(call: Call<UploadReceiptModelResponse>?, t: Throwable?) {
                createUploadReceiptResponseLD.postValue(null)
                Log.d("messageMultipart", "failed $t")
                Log.d("messageMultipartCall", "failed $call")
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
                Log.d("messageMultipart", "$response")
            }

        })
    }
}