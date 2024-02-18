package com.inspire.techstore.fragments.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inspire.techstore.api.RetroServiceInterface
import com.inspire.techstore.api.RetrofitInstance
import com.inspire.techstore.api.data.ProductModelItem
import com.inspire.techstore.api.data.UserCart
import com.inspire.techstore.db.history.History
import com.inspire.techstore.db.history.HistoryViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderFragmentViewModel(private var viewModelProvider: ViewModelProvider): ViewModel() {

    var liveDataList: MutableLiveData<List<ProductModelItem>?> = MutableLiveData()
    var liveDataTotalPrice: MutableLiveData<Double> = MutableLiveData()
    private lateinit var historyViewModel: HistoryViewModel

    fun addToHistory(history: History){
        historyViewModel = viewModelProvider[HistoryViewModel::class.java]
        historyViewModel.addToHistory(history)
    }

    fun getLiveDataObserver(): MutableLiveData<List<ProductModelItem>?> {
        return liveDataList
    }

    fun calculateTotalPrice(products: List<ProductModelItem>?) {
        var total = 0.0
        products?.forEach { product ->
            total += product.price
        }
        liveDataTotalPrice.postValue(total)
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
                    calculateTotalPrice(products)
                }
            })
        }
    }
}
