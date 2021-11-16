package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.OrdrChkCusAccsModel
import com.altwav.samquicksal2.models.QrReqAccessModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class OrdrChkCusAccsViewModel : ViewModel() {
    var createOrdrChkCusAccsResponseLD: MutableLiveData<OrdrChkCusAccsModel> =
        MutableLiveData()

    fun getOrdrChkCusAccsObserver(): MutableLiveData<OrdrChkCusAccsModel> {
        return createOrdrChkCusAccsResponseLD
    }

    fun getOrdrChkCusAccsInfo(cust_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.ordrCheckCusAccess(cust_id)
        call.enqueue(object : retrofit2.Callback<OrdrChkCusAccsModel> {
            override fun onFailure(call: Call<OrdrChkCusAccsModel>?, t: Throwable?) {
                createOrdrChkCusAccsResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<OrdrChkCusAccsModel>?,
                response: Response<OrdrChkCusAccsModel>?
            ) {
                if(response != null){
                    createOrdrChkCusAccsResponseLD.postValue(response.body())
                } else {
                    createOrdrChkCusAccsResponseLD.postValue(null)
                }
            }

        })
    }
}