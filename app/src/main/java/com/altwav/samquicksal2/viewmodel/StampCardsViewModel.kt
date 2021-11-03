package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.RestaurantMenuModelResponse
import com.altwav.samquicksal2.models.StampCardsModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class StampCardsViewModel: ViewModel() {
    var createStampCardsResponseLD: MutableLiveData<List<StampCardsModel>> =
        MutableLiveData()

    fun getStampCardsObserver(): MutableLiveData<List<StampCardsModel>> {
        return createStampCardsResponseLD
    }

    fun getStampCardsInfo(cust_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.stampCards(cust_id)
        call.enqueue(object : retrofit2.Callback<List<StampCardsModel>> {
            override fun onFailure(call: Call<List<StampCardsModel>>?, t: Throwable?) {
                createStampCardsResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<List<StampCardsModel>>?,
                response: Response<List<StampCardsModel>>?
            ) {
                if(response != null){
                    createStampCardsResponseLD.postValue(response.body())
                } else {
                    createStampCardsResponseLD.postValue(null)
                }
            }

        })
    }
}