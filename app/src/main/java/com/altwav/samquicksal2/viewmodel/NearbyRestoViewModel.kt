package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.NearbyRestoModel
import com.altwav.samquicksal2.models.NearbyRestoModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class NearbyRestoViewModel: ViewModel() {
    var createNearbyRestoModelResponseLD: MutableLiveData<List<NearbyRestoModelResponse>> =
        MutableLiveData()

    fun getNearbyRestoObserver(): MutableLiveData<List<NearbyRestoModelResponse>> {
        return createNearbyRestoModelResponseLD
    }

    fun getNearbyRestoInfo(customer: NearbyRestoModel){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getNearbyResto(customer)
        call.enqueue(object : retrofit2.Callback<List<NearbyRestoModelResponse>> {
            override fun onFailure(call: Call<List<NearbyRestoModelResponse>>?, t: Throwable?) {
                createNearbyRestoModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<List<NearbyRestoModelResponse>>?,
                response: Response<List<NearbyRestoModelResponse>>?
            ) {
                if(response != null){
                    createNearbyRestoModelResponseLD.postValue(response.body())
                } else {
                    createNearbyRestoModelResponseLD.postValue(null)
                }
            }

        })
    }
}