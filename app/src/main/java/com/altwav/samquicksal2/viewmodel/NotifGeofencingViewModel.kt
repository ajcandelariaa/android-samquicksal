package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.NotifDeclinedModel
import com.altwav.samquicksal2.models.NotifGeofencingModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class NotifGeofencingViewModel: ViewModel() {
    var createNotifGeofencingModelResponseLD: MutableLiveData<NotifGeofencingModel> =
        MutableLiveData()

    fun getNotifGeofencingObserver(): MutableLiveData<NotifGeofencingModel> {
        return createNotifGeofencingModelResponseLD
    }

    fun getNotifGeofencingInfo(cust_id: Int, notif_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.notifGeofencing(cust_id, notif_id)
        call.enqueue(object : retrofit2.Callback<NotifGeofencingModel> {
            override fun onFailure(call: Call<NotifGeofencingModel>?, t: Throwable?) {
                createNotifGeofencingModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<NotifGeofencingModel>?,
                response: Response<NotifGeofencingModel>?
            ) {
                if(response != null){
                    createNotifGeofencingModelResponseLD.postValue(response.body())
                } else {
                    createNotifGeofencingModelResponseLD.postValue(null)
                }
            }

        })
    }
}