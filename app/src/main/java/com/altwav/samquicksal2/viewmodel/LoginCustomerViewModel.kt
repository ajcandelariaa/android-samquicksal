package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.LoginCustomerModel
import com.altwav.samquicksal2.models.LoginCustomerModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class LoginCustomerViewModel: ViewModel() {
    var createLoginCustomerModelResponseLD: MutableLiveData<LoginCustomerModelResponse> =
        MutableLiveData()

    fun getLoginCustomerObserver(): MutableLiveData<LoginCustomerModelResponse> {
        return createLoginCustomerModelResponseLD
    }

    fun loginCustomer(customer: LoginCustomerModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.loginCustomer(customer)
        call.enqueue(object : retrofit2.Callback<LoginCustomerModelResponse> {
            override fun onFailure(call: Call<LoginCustomerModelResponse>?, t: Throwable?) {
                createLoginCustomerModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<LoginCustomerModelResponse>?,
                response: Response<LoginCustomerModelResponse>?
            ) {
                if(response != null){
                    createLoginCustomerModelResponseLD.postValue(response.body())
                } else {
                    createLoginCustomerModelResponseLD.postValue(null)
                }
            }

        })
    }
}