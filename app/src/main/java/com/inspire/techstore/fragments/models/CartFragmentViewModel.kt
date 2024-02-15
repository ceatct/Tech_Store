package com.inspire.techstore.fragments.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inspire.techstore.api.RetroServiceInterface
import com.inspire.techstore.api.RetrofitInstance
import com.inspire.techstore.api.data.ProductModelItem
import com.inspire.techstore.api.data.UserCart
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartFragmentViewModel: ViewModel() {

    var liveDataList: MutableLiveData<List<ProductModelItem>?> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<List<ProductModelItem>?> {
        return liveDataList
    }

    fun makeAPICall() {
        val retroInstance =  RetrofitInstance.getRetrofitInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)
        val call = retroService.getCart()

        call.enqueue(object : Callback<UserCart> {

            override fun onFailure(call: Call<UserCart>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(
                call: Call<UserCart>,
                response: Response<UserCart>
            ) {
                response.body()?.let { cart ->
                    val productIds = cart.products.map { it.productId.toString() }
                    makeAPICallItem(productIds)
                }
            }

        })
    }

    fun makeAPICallItem(ids: List<String>) {
        val retroInstance = RetrofitInstance.getRetrofitInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)
        val products = mutableListOf<ProductModelItem>()
        ids.forEach { id ->
            val call = retroService.getSingleProduct(id)
            call.enqueue(object : Callback<ProductModelItem> {

                override fun onFailure(call: Call<ProductModelItem>, t: Throwable) {
                    liveDataList.postValue(null)
                }

                override fun onResponse(
                    call: Call<ProductModelItem>,
                    response: Response<ProductModelItem>
                ) {
                    response.body()?.let { products.add(it) }
                    liveDataList.postValue(products)
                }
            })
        }
    }
}
