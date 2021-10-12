package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.AccountCustomerModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class AccountCustomerViewModel: ViewModel() {
    var createAccountCustomerModelResponseLD: MutableLiveData<AccountCustomerModelResponse> =
        MutableLiveData()

    fun getAccountCustomerObserver(): MutableLiveData<AccountCustomerModelResponse> {
        return createAccountCustomerModelResponseLD
    }

    fun getAccountInfoCustomer(customer: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getAccountCustomerInfo(customer)
        call.enqueue(object : retrofit2.Callback<AccountCustomerModelResponse> {
            override fun onFailure(call: Call<AccountCustomerModelResponse>?, t: Throwable?) {
                createAccountCustomerModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<AccountCustomerModelResponse>?,
                response: Response<AccountCustomerModelResponse>?
            ) {
                if(response != null){
                    createAccountCustomerModelResponseLD.postValue(response.body())
                } else {
                    createAccountCustomerModelResponseLD.postValue(null)
                }
            }

        })
    }
}