package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.UpdatePasswordModel
import com.altwav.samquicksal2.models.UpdatePasswordModelResponse
import com.altwav.samquicksal2.models.VerifyEmailModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class VerifyEmailViewModel: ViewModel() {
    var createVerifyEmailResponseLD: MutableLiveData<VerifyEmailModel> =
        MutableLiveData()

    fun getVerifyEmailObserver(): MutableLiveData<VerifyEmailModel> {
        return createVerifyEmailResponseLD
    }

    fun getVerifyEmailInfo(cust_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.verifyEmail(cust_id)
        call.enqueue(object : retrofit2.Callback<VerifyEmailModel> {
            override fun onFailure(call: Call<VerifyEmailModel>?, t: Throwable?) {
                createVerifyEmailResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<VerifyEmailModel>?,
                response: Response<VerifyEmailModel>?
            ) {
                if(response != null){
                    createVerifyEmailResponseLD.postValue(response.body())
                } else {
                    createVerifyEmailResponseLD.postValue(null)
                }
            }

        })
    }
}