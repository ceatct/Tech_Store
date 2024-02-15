package com.inspire.techstore.fragments.models

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inspire.techstore.api.RetroServiceInterface
import com.inspire.techstore.api.RetrofitInstance
import com.inspire.techstore.api.data.Cart
import com.inspire.techstore.api.data.CartProduct
import com.inspire.techstore.api.data.ProductModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewFragmentViewModel : ViewModel() {

    var liveDataList: MutableLiveData<ProductModelItem?> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<ProductModelItem?> {
        return liveDataList
    }

    fun makeAPICall(id: String) {
        val retroInstance = RetrofitInstance.getRetrofitInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)
        val call = retroService.getSingleProduct(id)

        call.enqueue(object : Callback<ProductModelItem> {

            override fun onFailure(call: Call<ProductModelItem>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(
                call: Call<ProductModelItem>,
                response: Response<ProductModelItem>
            ) {
                liveDataList.postValue(response.body())
            }

        })

    }

    suspend fun addToCart(userId: Int, date: String, products: List<CartProduct>, context: Context): Boolean {
        val service = RetrofitInstance.getRetrofitInstance().create(RetroServiceInterface::class.java)

        val cart = Cart(
            userId = userId,
            date = date,
            products = products
        )

        val response = service.addToCart(cart)
        return if (response.isSuccessful) {
            Toast.makeText(context, "Product added to cart successfully!", Toast.LENGTH_SHORT).show()
            true
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            Toast.makeText(context, "Error adding product: $errorBody", Toast.LENGTH_SHORT).show()
            false
        }
    }

}