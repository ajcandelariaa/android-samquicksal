package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.UpdatePasswordModel
import com.altwav.samquicksal2.models.UpdatePasswordModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class UpdatePasswordViewModel: ViewModel() {
    var createUpdatePasswordResponseLD: MutableLiveData<UpdatePasswordModelResponse> =
        MutableLiveData()

    fun getUpdatePasswordObserver(): MutableLiveData<UpdatePasswordModelResponse> {
        return createUpdatePasswordResponseLD
    }

    fun getUpdatePasswordInfo(cust_info: UpdatePasswordModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.updatePassword(cust_info)
        call.enqueue(object : retrofit2.Callback<UpdatePasswordModelResponse> {
            override fun onFailure(call: Call<UpdatePasswordModelResponse>?, t: Throwable?) {
                createUpdatePasswordResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<UpdatePasswordModelResponse>?,
                response: Response<UpdatePasswordModelResponse>?
            ) {
                if(response != null){
                    createUpdatePasswordResponseLD.postValue(response.body())
                } else {
                    createUpdatePasswordResponseLD.postValue(null)
                }
            }

        })
    }
}