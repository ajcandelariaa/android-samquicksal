package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.NotificationListModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class NotificationListViewModel:ViewModel() {
    var createListOfNotificationsResponseLD: MutableLiveData<List<NotificationListModelResponse>> =
        MutableLiveData()

    fun getNotificationListObserver(): MutableLiveData<List<NotificationListModelResponse>> {
        return createListOfNotificationsResponseLD
    }

    fun getNotificationsInfo(customer: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getNotificationsList(customer)
        call.enqueue(object : retrofit2.Callback<List<NotificationListModelResponse>> {
            override fun onFailure(call: Call<List<NotificationListModelResponse>>?, t: Throwable?) {
                createListOfNotificationsResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<List<NotificationListModelResponse>>?,
                response: Response<List<NotificationListModelResponse>>?
            ) {
                if(response != null){
                    createListOfNotificationsResponseLD.postValue(response.body())
                } else {
                    createListOfNotificationsResponseLD.postValue(null)
                }
            }

        })
    }
}