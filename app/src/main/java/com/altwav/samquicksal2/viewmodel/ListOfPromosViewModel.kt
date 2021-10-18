package com.altwav.samquicksal2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altwav.samquicksal2.models.ListOfPromosModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Response

class ListOfPromosViewModel: ViewModel(){
    var createListOfPromosResponseLD: MutableLiveData<List<ListOfPromosModel>> =
        MutableLiveData()

    fun getListOfPromosObserver(): MutableLiveData<List<ListOfPromosModel>> {
        return createListOfPromosResponseLD
    }

    fun getPromosInfo(){
        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getPromosList()
        call.enqueue(object : retrofit2.Callback<List<ListOfPromosModel>> {
            override fun onFailure(call: Call<List<ListOfPromosModel>>?, t: Throwable?) {
                createListOfPromosResponseLD.postValue(null)
            }

            override fun onResponse(
                call: Call<List<ListOfPromosModel>>?,
                response: Response<List<ListOfPromosModel>>?
            ) {
                if(response != null){
                    createListOfPromosResponseLD.postValue(response.body())
                } else {
                    createListOfPromosResponseLD.postValue(null)
                }
            }

        })
    }
}