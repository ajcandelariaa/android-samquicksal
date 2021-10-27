package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.NotifApprovedModel
import com.altwav.samquicksal2.models.NotifCancelledModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class NotifCancelledViewModel: ViewModel() {
    var createNotifCancelledModelResponseLD: MutableLiveData<NotifCancelledModel> =
        MutableLiveData()

    fun getNotifCancelledObserver(): MutableLiveData<NotifCancelledModel> {
        return createNotifCancelledModelResponseLD
    }

    fun getNotifCancelledInfo(cust_id: Int, notif_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.notifCancelled(cust_id, notif_id)
        call.enqueue(object : retrofit2.Callback<NotifCancelledModel> {
            override fun onFailure(call: Call<NotifCancelledModel>?, t: Throwable?) {
                createNotifCancelledModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<NotifCancelledModel>?,
                response: Response<NotifCancelledModel>?
            ) {
                if(response != null){
                    createNotifCancelledModelResponseLD.postValue(response.body())
                } else {
                    createNotifCancelledModelResponseLD.postValue(null)
                }
            }

        })
    }
}