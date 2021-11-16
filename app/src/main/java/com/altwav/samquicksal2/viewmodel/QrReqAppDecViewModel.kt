package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.QrReqAccessModel
import com.altwav.samquicksal2.models.QrReqAppDecModel
import com.altwav.samquicksal2.models.QrReqAppDecModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class QrReqAppDecViewModel : ViewModel() {
    var createQrReqAppDecResponseLD: MutableLiveData<QrReqAppDecModelResponse> =
        MutableLiveData()

    fun getQrReqAppDecObserver(): MutableLiveData<QrReqAppDecModelResponse> {
        return createQrReqAppDecResponseLD
    }

    fun getQrReqAppDecInfo(custRequest: QrReqAppDecModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.qrReqAppDec(custRequest)
        call.enqueue(object : retrofit2.Callback<QrReqAppDecModelResponse> {
            override fun onFailure(call: Call<QrReqAppDecModelResponse>?, t: Throwable?) {
                createQrReqAppDecResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<QrReqAppDecModelResponse>?,
                response: Response<QrReqAppDecModelResponse>?
            ) {
                if(response != null){
                    createQrReqAppDecResponseLD.postValue(response.body())
                } else {
                    createQrReqAppDecResponseLD.postValue(null)
                }
            }

        })
    }
}