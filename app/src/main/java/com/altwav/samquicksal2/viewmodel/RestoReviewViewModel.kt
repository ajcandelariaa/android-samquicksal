package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.RestoReviewModel
import com.altwav.samquicksal2.models.StampDetailsModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class RestoReviewViewModel: ViewModel() {
    var createRestoReviewResponseLD: MutableLiveData<RestoReviewModel> =
        MutableLiveData()

    fun getRestoReviewObserver(): MutableLiveData<RestoReviewModel> {
        return createRestoReviewResponseLD
    }

    fun getRestoReviewInfo(rest_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getRestaurantReviews(rest_id)
        call.enqueue(object : retrofit2.Callback<RestoReviewModel> {
            override fun onFailure(call: Call<RestoReviewModel>?, t: Throwable?) {
                createRestoReviewResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<RestoReviewModel>?,
                response: Response<RestoReviewModel>?
            ) {
                if(response != null){
                    createRestoReviewResponseLD.postValue(response.body())
                } else {
                    createRestoReviewResponseLD.postValue(null)
                }
            }

        })
    }
}