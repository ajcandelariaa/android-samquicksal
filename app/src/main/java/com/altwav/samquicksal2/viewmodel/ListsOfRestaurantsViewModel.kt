package com.altwav.samquicksal2.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.ListOfRestaurantModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class ListsOfRestaurantsViewModel:ViewModel() {
    var liveDataList: MutableLiveData<List<ListOfRestaurantModel>>

    init {
        liveDataList = MutableLiveData()
    }

    fun getLiveDataObserver(): MutableLiveData<List<ListOfRestaurantModel>> {
        return liveDataList
    }

    fun makeApiCall(){
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)
        val call = retroService.getRestaurantList()
        call.enqueue(object : retrofit2.Callback<List<ListOfRestaurantModel>> {
            override fun onFailure(call: Call<List<ListOfRestaurantModel>>?, t: Throwable?) {
                liveDataList.postValue(null)
            }

            override fun onResponse(
                call: Call<List<ListOfRestaurantModel>>?,
                response: Response<List<ListOfRestaurantModel>>?
            ) {
                if (response != null) {
                    liveDataList.postValue(response.body())
                }
            }
        })
    }
}