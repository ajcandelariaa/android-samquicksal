package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.RFStatusModel
import com.altwav.samquicksal2.models.RFSubmitFormModel
import com.altwav.samquicksal2.models.RFSubmitFormModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class RFSubmitFormViewModel: ViewModel() {
    var createRFSubmitFormResponseLD: MutableLiveData<RFSubmitFormModelResponse> =
        MutableLiveData()

    fun getRFSubmitFormObserver(): MutableLiveData<RFSubmitFormModelResponse> {
        return createRFSubmitFormResponseLD
    }

    fun getRFSubmitFormInfo(form: RFSubmitFormModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.ratingFBSubmit(form)
        call.enqueue(object : retrofit2.Callback<RFSubmitFormModelResponse> {
            override fun onFailure(call: Call<RFSubmitFormModelResponse>?, t: Throwable?) {
                createRFSubmitFormResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<RFSubmitFormModelResponse>?,
                response: Response<RFSubmitFormModelResponse>?
            ) {
                if(response != null){
                    createRFSubmitFormResponseLD.postValue(response.body())
                } else {
                    createRFSubmitFormResponseLD.postValue(null)
                }
            }

        })
    }
}