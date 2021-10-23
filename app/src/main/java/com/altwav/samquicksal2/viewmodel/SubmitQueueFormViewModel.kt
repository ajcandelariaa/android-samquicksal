package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.SubmitQueueFormModel
import com.altwav.samquicksal2.models.SubmitQueueFormModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class SubmitQueueFormViewModel:ViewModel() {
    var createQueueFormModelResponseLD: MutableLiveData<SubmitQueueFormModelResponse> =
        MutableLiveData()

    fun getQueueFormObserver(): MutableLiveData<SubmitQueueFormModelResponse> {
        return createQueueFormModelResponseLD
    }

    fun submitQueueForm(customer: SubmitQueueFormModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.submitQueueForm(customer)
        call.enqueue(object : retrofit2.Callback<SubmitQueueFormModelResponse> {
            override fun onFailure(call: Call<SubmitQueueFormModelResponse>?, t: Throwable?) {
                createQueueFormModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<SubmitQueueFormModelResponse>?,
                response: Response<SubmitQueueFormModelResponse>?
            ) {
                if(response != null){
                    createQueueFormModelResponseLD.postValue(response.body())
                } else {
                    createQueueFormModelResponseLD.postValue(null)
                }
            }

        })
    }
}