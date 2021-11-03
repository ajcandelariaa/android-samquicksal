package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.StampCardsModel
import com.altwav.samquicksal2.models.StampDetailsModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class StampDetailsViewModel: ViewModel() {
    var createStampDetailsResponseLD: MutableLiveData<StampDetailsModel> =
        MutableLiveData()

    fun getStampDetailsObserver(): MutableLiveData<StampDetailsModel> {
        return createStampDetailsResponseLD
    }

    fun getStampDetailsInfo(stamp_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.stampCardDetails(stamp_id)
        call.enqueue(object : retrofit2.Callback<StampDetailsModel> {
            override fun onFailure(call: Call<StampDetailsModel>?, t: Throwable?) {
                createStampDetailsResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<StampDetailsModel>?,
                response: Response<StampDetailsModel>?
            ) {
                if(response != null){
                    createStampDetailsResponseLD.postValue(response.body())
                } else {
                    createStampDetailsResponseLD.postValue(null)
                }
            }

        })
    }
}