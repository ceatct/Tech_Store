package com.inspire.techstore.api

import com.inspire.techstore.api.data.ProductModel
import retrofit2.Call
import retrofit2.http.GET

interface RetroServiceInterface {

    @GET("products")
    fun getProductsList(): Call<List<ProductModel>>

}