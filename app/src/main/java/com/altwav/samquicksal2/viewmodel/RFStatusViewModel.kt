package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.RFStatusModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class RFStatusViewModel: ViewModel() {
    var createRFStatusResponseLD: MutableLiveData<RFStatusModel> =
        MutableLiveData()

    fun getRFStatusObserver(): MutableLiveData<RFStatusModel> {
        return createRFStatusResponseLD
    }

    fun getRFStatusInfo(cust_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.ratingFeedback(cust_id)
        call.enqueue(object : retrofit2.Callback<RFStatusModel> {
            override fun onFailure(call: Call<RFStatusModel>?, t: Throwable?) {
                createRFStatusResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<RFStatusModel>?,
                response: Response<RFStatusModel>?
            ) {
                if(response != null){
                    createRFStatusResponseLD.postValue(response.body())
                } else {
                    createRFStatusResponseLD.postValue(null)
                }
            }

        })
    }
}