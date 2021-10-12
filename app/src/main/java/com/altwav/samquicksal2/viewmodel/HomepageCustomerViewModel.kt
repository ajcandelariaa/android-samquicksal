package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.HomepageCustomerModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class HomepageCustomerViewModel: ViewModel() {
    var createHomepageCustomerModelResponseLD: MutableLiveData<HomepageCustomerModelResponse> =
        MutableLiveData()

    fun getHomepageCustomerObserver(): MutableLiveData<HomepageCustomerModelResponse> {
        return createHomepageCustomerModelResponseLD
    }

    fun getHomepageInfoCustomer(customer: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getHomepageCustomerInfo(customer)
        call.enqueue(object : retrofit2.Callback<HomepageCustomerModelResponse> {
            override fun onFailure(call: Call<HomepageCustomerModelResponse>?, t: Throwable?) {
                createHomepageCustomerModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<HomepageCustomerModelResponse>?,
                response: Response<HomepageCustomerModelResponse>?
            ) {
                if(response != null){
                    createHomepageCustomerModelResponseLD.postValue(response.body())
                } else {
                    createHomepageCustomerModelResponseLD.postValue(null)
                }
            }

        })
    }
}