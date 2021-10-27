package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.ForgotPasswordModel
import com.altwav.samquicksal2.models.ForgotPasswordModelResponse
import com.altwav.samquicksal2.models.NotifApprovedModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class ForgotPasswordViewModel: ViewModel() {
    var createForgotPasswordModelResponseLD: MutableLiveData<ForgotPasswordModelResponse> =
        MutableLiveData()

    fun getForgotPasswordObserver(): MutableLiveData<ForgotPasswordModelResponse> {
        return createForgotPasswordModelResponseLD
    }

    fun getForgotPasswordInfo(emailAddress: ForgotPasswordModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.forgotPassword(emailAddress)
        call.enqueue(object : retrofit2.Callback<ForgotPasswordModelResponse> {
            override fun onFailure(call: Call<ForgotPasswordModelResponse>?, t: Throwable?) {
                createForgotPasswordModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<ForgotPasswordModelResponse>?,
                response: Response<ForgotPasswordModelResponse>?
            ) {
                if(response != null){
                    createForgotPasswordModelResponseLD.postValue(response.body())
                } else {
                    createForgotPasswordModelResponseLD.postValue(null)
                }
            }

        })
    }
}