package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.NotifCancelledModel
import com.altwav.samquicksal2.models.NotifDeclinedModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class NotifDeclinedViewModel: ViewModel() {
    var createNotifDeclinedModelResponseLD: MutableLiveData<NotifDeclinedModel> =
        MutableLiveData()

    fun getNotifDeclinedObserver(): MutableLiveData<NotifDeclinedModel> {
        return createNotifDeclinedModelResponseLD
    }

    fun getNotifDeclinedInfo(cust_id: Int, notif_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.notifDeclined(cust_id, notif_id)
        call.enqueue(object : retrofit2.Callback<NotifDeclinedModel> {
            override fun onFailure(call: Call<NotifDeclinedModel>?, t: Throwable?) {
                createNotifDeclinedModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<NotifDeclinedModel>?,
                response: Response<NotifDeclinedModel>?
            ) {
                if(response != null){
                    createNotifDeclinedModelResponseLD.postValue(response.body())
                } else {
                    createNotifDeclinedModelResponseLD.postValue(null)
                }
            }

        })
    }
}