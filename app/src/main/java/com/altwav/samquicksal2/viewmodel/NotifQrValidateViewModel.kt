package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.NotifQrValidateModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class NotifQrValidateViewModel: ViewModel() {
    var createNotifQrValidateModelResponseLD: MutableLiveData<NotifQrValidateModel> =
        MutableLiveData()

    fun getNotifQrValidateObserver(): MutableLiveData<NotifQrValidateModel> {
        return createNotifQrValidateModelResponseLD
    }

    fun getNotifQrValidateInfo(cust_id: Int, notif_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.notifQrValidate(cust_id, notif_id)
        call.enqueue(object : retrofit2.Callback<NotifQrValidateModel> {
            override fun onFailure(call: Call<NotifQrValidateModel>?, t: Throwable?) {
                createNotifQrValidateModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<NotifQrValidateModel>?,
                response: Response<NotifQrValidateModel>?
            ) {
                if(response != null){
                    createNotifQrValidateModelResponseLD.postValue(response.body())
                } else {
                    createNotifQrValidateModelResponseLD.postValue(null)
                }
            }

        })
    }
}