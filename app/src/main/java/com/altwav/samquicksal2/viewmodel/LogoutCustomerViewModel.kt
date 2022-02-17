package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.LogoutCustomerModel
import com.altwav.samquicksal2.models.LogoutCustomerModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class LogoutCustomerViewModel: ViewModel() {
    var createLogoutCustomerModelResponseLD: MutableLiveData<LogoutCustomerModelResponse> =
        MutableLiveData()

    fun getLogoutCustomerObserver(): MutableLiveData<LogoutCustomerModelResponse> {
        return createLogoutCustomerModelResponseLD
    }

    fun getLogoutInfoCustomer(customer: LogoutCustomerModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.logoutCustomer(customer)
        call.enqueue(object : retrofit2.Callback<LogoutCustomerModelResponse> {
            override fun onFailure(call: Call<LogoutCustomerModelResponse>?, t: Throwable?) {
                createLogoutCustomerModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<LogoutCustomerModelResponse>?,
                response: Response<LogoutCustomerModelResponse>?
            ) {
                if(response != null){
                    createLogoutCustomerModelResponseLD.postValue(response.body())
                } else {
                    createLogoutCustomerModelResponseLD.postValue(null)
                }
            }

        })
    }
}