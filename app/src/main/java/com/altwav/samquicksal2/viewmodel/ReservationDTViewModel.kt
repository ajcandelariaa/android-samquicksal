package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.ReservationDTModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class ReservationDTViewModel: ViewModel() {
    var createReservationDTResponseLD: MutableLiveData<ReservationDTModel> =
        MutableLiveData()

    fun getReservationDTObserver(): MutableLiveData<ReservationDTModel> {
        return createReservationDTResponseLD
    }

    fun getReservationDTInfo(rest_id: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getReservationDT(rest_id)
        call.enqueue(object : retrofit2.Callback<ReservationDTModel> {
            override fun onFailure(call: Call<ReservationDTModel>?, t: Throwable?) {
                createReservationDTResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<ReservationDTModel>?,
                response: Response<ReservationDTModel>?
            ) {
                if(response != null){
                    createReservationDTResponseLD.postValue(response.body())
                } else {
                    createReservationDTResponseLD.postValue(null)
                }
            }

        })
    }
}