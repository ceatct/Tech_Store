package com.inspire.techstore.fragments.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inspire.techstore.api.RetroServiceInterface
import com.inspire.techstore.api.RetrofitInstance
import com.inspire.techstore.api.data.ProductModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragmentViewModel: ViewModel() {

    lateinit var liveDataList: MutableLiveData<List<ProductModel>>

    init {
        liveDataList = MutableLiveData()
    }

    fun getLiveDataObserver(): MutableLiveData<List<ProductModel>> {
        return liveDataList
    }

    fun makeAPICall() {
        val retroInstance =  RetrofitInstance. getRetrofitInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)
        val call = retroService.getProductsList()

        call.enqueue(object : Callback<List<ProductModel>> {

            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(
                call: Call<List<ProductModel>>,
                response: Response<List<ProductModel>>
            ) {
                liveDataList.postValue(response.body())
            }

        })


    }


}