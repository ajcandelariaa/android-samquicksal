package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.UpdateNameModel
import com.altwav.samquicksal2.models.UpdateNameModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class UpdateNameViewModel: ViewModel() {
    var createUpdateNameResponseLD: MutableLiveData<UpdateNameModelResponse> =
        MutableLiveData()

    fun getUpdateNameObserver(): MutableLiveData<UpdateNameModelResponse> {
        return createUpdateNameResponseLD
    }

    fun getUpdateNameInfo(cust_info: UpdateNameModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.updateSingleInfo(cust_info)
        call.enqueue(object : retrofit2.Callback<UpdateNameModelResponse> {
            override fun onFailure(call: Call<UpdateNameModelResponse>?, t: Throwable?) {
                createUpdateNameResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<UpdateNameModelResponse>?,
                response: Response<UpdateNameModelResponse>?
            ) {
                if(response != null){
                    createUpdateNameResponseLD.postValue(response.body())
                } else {
                    createUpdateNameResponseLD.postValue(null)
                }
            }

        })
    }
}