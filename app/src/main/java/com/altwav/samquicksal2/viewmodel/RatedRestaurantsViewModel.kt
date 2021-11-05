package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.RatedRestaurantsModel
import com.altwav.samquicksal2.models.StampCardsModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class RatedRestaurantsViewModel: ViewModel() {
    var createRatedRestaurantsResponseLD: MutableLiveData<List<RatedRestaurantsModel>> =
        MutableLiveData()

    fun getRatedRestaurantsObserver(): MutableLiveData<List<RatedRestaurantsModel>> {
        return createRatedRestaurantsResponseLD
    }

    fun getRatedRestaurantsInfo(){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getRatedRestaurants()
        call.enqueue(object : retrofit2.Callback<List<RatedRestaurantsModel>> {
            override fun onFailure(call: Call<List<RatedRestaurantsModel>>?, t: Throwable?) {
                createRatedRestaurantsResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<List<RatedRestaurantsModel>>?,
                response: Response<List<RatedRestaurantsModel>>?
            ) {
                if(response != null){
                    createRatedRestaurantsResponseLD.postValue(response.body())
                } else {
                    createRatedRestaurantsResponseLD.postValue(null)
                }
            }

        })
    }
}