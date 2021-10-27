package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.NotifNoShowModel
import com.altwav.samquicksal2.models.NotifRunawayModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class NotifRunawayViewModel: ViewModel() {
    var createNotifRunawayModelResponseLD: MutableLiveData<NotifRunawayModel> =
        MutableLiveData()

    fun getNotifRunawayObserver(): MutableLiveData<NotifRunawayModel> {
        return createNotifRunawayModelResponseLD
    }

    fun getNotifRunawayInfo(cust_id: Int, notif_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.notifRunaway(cust_id, notif_id)
        call.enqueue(object : retrofit2.Callback<NotifRunawayModel> {
            override fun onFailure(call: Call<NotifRunawayModel>?, t: Throwable?) {
                createNotifRunawayModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<NotifRunawayModel>?,
                response: Response<NotifRunawayModel>?
            ) {
                if(response != null){
                    createNotifRunawayModelResponseLD.postValue(response.body())
                } else {
                    createNotifRunawayModelResponseLD.postValue(null)
                }
            }

        })
    }
}