package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.QrReqAccessModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class QrReqAccessViewModel : ViewModel() {
    var createQrReqAccessResponseLD: MutableLiveData<QrReqAccessModel> =
        MutableLiveData()

    fun getQrReqAccessObserver(): MutableLiveData<QrReqAccessModel> {
        return createQrReqAccessResponseLD
    }

    fun getQrReqAccessInfo(cust_id: Int, request_cust_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.orderingReqAccess(cust_id, request_cust_id)
        call.enqueue(object : retrofit2.Callback<QrReqAccessModel> {
            override fun onFailure(call: Call<QrReqAccessModel>?, t: Throwable?) {
                createQrReqAccessResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<QrReqAccessModel>?,
                response: Response<QrReqAccessModel>?
            ) {
                if(response != null){
                    createQrReqAccessResponseLD.postValue(response.body())
                } else {
                    createQrReqAccessResponseLD.postValue(null)
                }
            }

        })
    }
}