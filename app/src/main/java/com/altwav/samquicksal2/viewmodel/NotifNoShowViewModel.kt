package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.NotifDeclinedModel
import com.altwav.samquicksal2.models.NotifNoShowModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class NotifNoShowViewModel: ViewModel() {
    var createNotifNoShowModelResponseLD: MutableLiveData<NotifNoShowModel> =
        MutableLiveData()

    fun getNotifNoShowObserver(): MutableLiveData<NotifNoShowModel> {
        return createNotifNoShowModelResponseLD
    }

    fun getNotifNoShowInfo(cust_id: Int, notif_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.notifNoShow(cust_id, notif_id)
        call.enqueue(object : retrofit2.Callback<NotifNoShowModel> {
            override fun onFailure(call: Call<NotifNoShowModel>?, t: Throwable?) {
                createNotifNoShowModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<NotifNoShowModel>?,
                response: Response<NotifNoShowModel>?
            ) {
                if(response != null){
                    createNotifNoShowModelResponseLD.postValue(response.body())
                } else {
                    createNotifNoShowModelResponseLD.postValue(null)
                }
            }

        })
    }
}