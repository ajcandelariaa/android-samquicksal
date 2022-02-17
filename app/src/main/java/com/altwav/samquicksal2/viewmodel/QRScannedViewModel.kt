package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.QRScannedModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class QRScannedViewModel: ViewModel() {
    var createQRScannedResponseLD: MutableLiveData<QRScannedModel> =
        MutableLiveData()

    fun getQrScannedObserver(): MutableLiveData<QRScannedModel> {
        return createQRScannedResponseLD
    }

    fun getQrScannedInfo(cust_id: Int, request_cust_id: String){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.scanQRCode(cust_id, request_cust_id)
        call.enqueue(object : retrofit2.Callback<QRScannedModel> {
            override fun onFailure(call: Call<QRScannedModel>?, t: Throwable?) {
                createQRScannedResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<QRScannedModel>?,
                response: Response<QRScannedModel>?
            ) {
                if(response != null){
                    createQRScannedResponseLD.postValue(response.body())
                } else {
                    createQRScannedResponseLD.postValue(null)
                }
            }

        })
    }
}