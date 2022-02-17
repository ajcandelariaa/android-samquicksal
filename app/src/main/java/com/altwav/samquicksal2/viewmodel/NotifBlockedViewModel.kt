package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.NotifBlockedModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class NotifBlockedViewModel: ViewModel() {
    var createNotifBlockedResponseLD: MutableLiveData<NotifBlockedModel> =
        MutableLiveData()

    fun getNotifBlockedObserver(): MutableLiveData<NotifBlockedModel> {
        return createNotifBlockedResponseLD
    }

    fun getNotifBlockedInfo(cust_id: Int, notif_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.notifBlocked(cust_id, notif_id)
        call.enqueue(object : retrofit2.Callback<NotifBlockedModel> {
            override fun onFailure(call: Call<NotifBlockedModel>?, t: Throwable?) {
                createNotifBlockedResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<NotifBlockedModel>?,
                response: Response<NotifBlockedModel>?
            ) {
                if(response != null){
                    createNotifBlockedResponseLD.postValue(response.body())
                } else {
                    createNotifBlockedResponseLD.postValue(null)
                }
            }

        })
    }
}