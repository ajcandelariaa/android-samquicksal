package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.NotifCompletedModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class NotifCompletedViewModel: ViewModel() {
    var createNotifCompletedModelResponseLD: MutableLiveData<NotifCompletedModel> =
        MutableLiveData()

    fun getNotifCompletedObserver(): MutableLiveData<NotifCompletedModel> {
        return createNotifCompletedModelResponseLD
    }

    fun getNotifCompletedInfo(cust_id: Int, notif_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.notifCompleted(cust_id, notif_id)
        call.enqueue(object : retrofit2.Callback<NotifCompletedModel> {
            override fun onFailure(call: Call<NotifCompletedModel>?, t: Throwable?) {
                createNotifCompletedModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<NotifCompletedModel>?,
                response: Response<NotifCompletedModel>?
            ) {
                if(response != null){
                    createNotifCompletedModelResponseLD.postValue(response.body())
                } else {
                    createNotifCompletedModelResponseLD.postValue(null)
                }
            }

        })
    }
}