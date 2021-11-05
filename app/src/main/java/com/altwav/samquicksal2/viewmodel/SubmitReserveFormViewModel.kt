package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.*
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class SubmitReserveFormViewModel: ViewModel() {
    var createReserveFormModelResponseLD: MutableLiveData<SubmitReserveFormModelResponse> =
        MutableLiveData()

    fun getReserveFormObserver(): MutableLiveData<SubmitReserveFormModelResponse> {
        return createReserveFormModelResponseLD
    }

    fun submitReserveForm(customer: SubmitReserveFormModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.submitReserveForm(customer)
        call.enqueue(object : retrofit2.Callback<SubmitReserveFormModelResponse> {
            override fun onFailure(call: Call<SubmitReserveFormModelResponse>?, t: Throwable?) {
                createReserveFormModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<SubmitReserveFormModelResponse>?,
                response: Response<SubmitReserveFormModelResponse>?
            ) {
                if(response != null){
                    createReserveFormModelResponseLD.postValue(response.body())
                } else {
                    createReserveFormModelResponseLD.postValue(null)
                }
            }

        })
    }
}