package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.LiveStatusModelResponse
import com.altwav.samquicksal2.models.NotifApprovedModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class NotifApprovedViewModel: ViewModel() {
    var createNotifApprovedModelResponseLD: MutableLiveData<NotifApprovedModel> =
        MutableLiveData()

    fun getNotifApprovedObserver(): MutableLiveData<NotifApprovedModel> {
        return createNotifApprovedModelResponseLD
    }

    fun getNotifApprovedInfo(cust_id: Int, notif_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.notifApproved(cust_id, notif_id)
        call.enqueue(object : retrofit2.Callback<NotifApprovedModel> {
            override fun onFailure(call: Call<NotifApprovedModel>?, t: Throwable?) {
                createNotifApprovedModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<NotifApprovedModel>?,
                response: Response<NotifApprovedModel>?
            ) {
                if(response != null){
                    createNotifApprovedModelResponseLD.postValue(response.body())
                } else {
                    createNotifApprovedModelResponseLD.postValue(null)
                }
            }

        })
    }
}