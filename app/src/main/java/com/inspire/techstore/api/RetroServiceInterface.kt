package com.inspire.techstore.api

import com.inspire.techstore.api.data.CategoriesModel
import com.inspire.techstore.api.data.ProductModelItem
import retrofit2.Call
import retrofit2.http.GET

interface RetroServiceInterface {
    @GET("products")
    fun getProductsList(): Call<List<ProductModelItem>>

    @GET("products/categories")
    fun getCategoriesList(): Call<CategoriesModel>

}