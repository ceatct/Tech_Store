package com.inspire.techstore.fragments.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inspire.techstore.api.RetroServiceInterface
import com.inspire.techstore.api.RetrofitInstance
import com.inspire.techstore.api.data.ProductModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultFragmentViewModel : ViewModel() {

    var liveDataList: MutableLiveData<List<ProductModelItem>> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<List<ProductModelItem>> {
        return liveDataList
    }

    fun makeAPICall(category: String) {
        val retroInstance =  RetrofitInstance. getRetrofitInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)
        val call = retroService.getProductsListByCategory(category)

        call.enqueue(object : Callback<List<ProductModelItem>> {

            override fun onFailure(call: Call<List<ProductModelItem>>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(
                call: Call<List<ProductModelItem>>,
                response: Response<List<ProductModelItem>>
            ) {
                liveDataList.postValue(response.body())
            }

        })

    }

}