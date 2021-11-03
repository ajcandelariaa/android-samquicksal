package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.CheckoutStatusModel
import com.altwav.samquicksal2.models.GCashCStatusModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class GCashStatusViewModel: ViewModel() {
    var createGCashStatusResponseLD: MutableLiveData<GCashCStatusModel> =
        MutableLiveData()

    fun getGCashStatusObserver(): MutableLiveData<GCashCStatusModel> {
        return createGCashStatusResponseLD
    }

    fun getGCashStatusInfo(cust_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.gCashCheckoutStatus(cust_id)
        call.enqueue(object : retrofit2.Callback<GCashCStatusModel> {
            override fun onFailure(call: Call<GCashCStatusModel>?, t: Throwable?) {
                createGCashStatusResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<GCashCStatusModel>?,
                response: Response<GCashCStatusModel>?
            ) {
                if(response != null){
                    createGCashStatusResponseLD.postValue(response.body())
                } else {
                    createGCashStatusResponseLD.postValue(null)
                }
            }

        })
    }
}