package com.altwav.samquicksal2.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.UpdateImageModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class UpdateImageViewModel: ViewModel() {
    var createUploadImageResponseLD: MutableLiveData<UpdateImageModel> =
        MutableLiveData()

    fun getUploadImageObserver(): MutableLiveData<UpdateImageModel> {
        return createUploadImageResponseLD
    }

    fun getUploadImageInfo(userProfile: MultipartBody.Part, cust_id: RequestBody){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.updateUserProfile(userProfile, cust_id)
        call.enqueue(object : retrofit2.Callback<UpdateImageModel> {
            override fun onFailure(call: Call<UpdateImageModel>?, t: Throwable?) {
                createUploadImageResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<UpdateImageModel>?,
                response: Response<UpdateImageModel>?
            ) {
                if(response != null){
                    createUploadImageResponseLD.postValue(response.body())
                } else {
                    createUploadImageResponseLD.postValue(null)
                }
            }

        })
    }
}