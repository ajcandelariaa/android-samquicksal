package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.BookingHistoryModelResponse
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class BookingHistoryViewModel: ViewModel() {
    var createBookingHistoryModelResponseLD: MutableLiveData<List<BookingHistoryModelResponse>> =
        MutableLiveData()

    fun getBookingHistoryObserver(): MutableLiveData<List<BookingHistoryModelResponse>> {
        return createBookingHistoryModelResponseLD
    }

    fun getBookingHistory(customer: Int){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.bookingHistory(customer)
        call.enqueue(object : retrofit2.Callback<List<BookingHistoryModelResponse>> {
            override fun onFailure(call: Call<List<BookingHistoryModelResponse>>?, t: Throwable?) {
                createBookingHistoryModelResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<List<BookingHistoryModelResponse>>?,
                response: Response<List<BookingHistoryModelResponse>>?
            ) {
                if(response != null){
                    createBookingHistoryModelResponseLD.postValue(response.body())
                } else {
                    createBookingHistoryModelResponseLD.postValue(null)
                }
            }

        })
    }
}