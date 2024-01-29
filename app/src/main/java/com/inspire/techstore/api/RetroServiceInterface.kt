package com.inspire.techstore.api

import com.inspire.techstore.api.data.CategoriesModel
import com.inspire.techstore.api.data.ProductModelItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetroServiceInterface {
    @GET("products?limit=5")
    fun getProductsList(): Call<List<ProductModelItem>>

    @GET("products/categories")
    fun getCategoriesList(): Call<CategoriesModel>

    @GET("products/category/{category}")
    fun getProductsListByCategory(@Path("category") category: String): Call<List<ProductModelItem>>

    @GET("products/{id}")
    fun getSingleProduct(@Path("id") id: String): Call<ProductModelItem>

}