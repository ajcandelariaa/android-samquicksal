package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.RegisterCustomerModel
import com.altwav.samquicksal2.models.RegisterCustomerModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class RegisterCustomerViewModel: ViewModel() {
    var createRegisterCustomerModelResponseLD: MutableLiveData<RegisterCustomerModelResponse> =
        MutableLiveData()

    fun getRegisterCustomerObserver(): MutableLiveData<RegisterCustomerModelResponse> {
        return createRegisterCustomerModelResponseLD
    }

    fun registerNewCustomer(customer: RegisterCustomerModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.registerCustomer(customer)
        call.enqueue(object : retrofit2.Callback<RegisterCustomerModelResponse> {
            override fun onFailure(call: Call<RegisterCustomerModelResponse>?, t: Throwable?) {
                createRegisterCustomerModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<RegisterCustomerModelResponse>?,
                response: Response<RegisterCustomerModelResponse>?
            ) {
                if(response != null){
                    createRegisterCustomerModelResponseLD.postValue(response.body())
                } else {
                    createRegisterCustomerModelResponseLD.postValue(null)
                }
            }

        })
    }
}