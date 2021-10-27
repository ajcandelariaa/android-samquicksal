package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.NotifApprovedModel
import com.altwav.samquicksal2.models.NotifPendingModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class NotifPendingViewModel: ViewModel() {
    var createNotifPendingModelResponseLD: MutableLiveData<NotifPendingModel> =
        MutableLiveData()

    fun getNotifPendingObserver(): MutableLiveData<NotifPendingModel> {
        return createNotifPendingModelResponseLD
    }

    fun getNotifPendingInfo(cust_id: Int, notif_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.notifPending(cust_id, notif_id)
        call.enqueue(object : retrofit2.Callback<NotifPendingModel> {
            override fun onFailure(call: Call<NotifPendingModel>?, t: Throwable?) {
                createNotifPendingModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<NotifPendingModel>?,
                response: Response<NotifPendingModel>?
            ) {
                if(response != null){
                    createNotifPendingModelResponseLD.postValue(response.body())
                } else {
                    createNotifPendingModelResponseLD.postValue(null)
                }
            }

        })
    }
}