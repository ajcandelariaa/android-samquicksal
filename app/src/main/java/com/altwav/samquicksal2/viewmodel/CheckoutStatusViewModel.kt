package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.CheckoutStatusModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class CheckoutStatusViewModel: ViewModel() {
    var createCheckoutStatusResponseLD: MutableLiveData<CheckoutStatusModel> =
        MutableLiveData()

    fun getCheckoutStatusObserver(): MutableLiveData<CheckoutStatusModel> {
        return createCheckoutStatusResponseLD
    }

    fun getCheckoutStatusInfo(cust_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.checkoutStatus(cust_id)
        call.enqueue(object : retrofit2.Callback<CheckoutStatusModel> {
            override fun onFailure(call: Call<CheckoutStatusModel>?, t: Throwable?) {
                createCheckoutStatusResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<CheckoutStatusModel>?,
                response: Response<CheckoutStatusModel>?
            ) {
                if(response != null){
                    createCheckoutStatusResponseLD.postValue(response.body())
                } else {
                    createCheckoutStatusResponseLD.postValue(null)
                }
            }

        })
    }
}