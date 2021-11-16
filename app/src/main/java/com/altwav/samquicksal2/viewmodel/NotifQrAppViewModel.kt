package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.NotifQrAppModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class NotifQrAppViewModel: ViewModel() {
    var createNotifQrAppModelResponseLD: MutableLiveData<NotifQrAppModel> =
        MutableLiveData()

    fun getNotifQrAppObserver(): MutableLiveData<NotifQrAppModel> {
        return createNotifQrAppModelResponseLD
    }

    fun getNotifQrAppInfo(cust_id: Int, notif_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.notifQrApproved(cust_id, notif_id)
        call.enqueue(object : retrofit2.Callback<NotifQrAppModel> {
            override fun onFailure(call: Call<NotifQrAppModel>?, t: Throwable?) {
                createNotifQrAppModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<NotifQrAppModel>?,
                response: Response<NotifQrAppModel>?
            ) {
                if(response != null){
                    createNotifQrAppModelResponseLD.postValue(response.body())
                } else {
                    createNotifQrAppModelResponseLD.postValue(null)
                }
            }

        })
    }
}