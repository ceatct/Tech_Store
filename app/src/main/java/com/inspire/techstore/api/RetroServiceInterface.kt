package com.inspire.techstore.api

import com.inspire.techstore.api.data.CategoriesModel
import com.inspire.techstore.api.data.ProductModelItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetroServiceInterface {
    @GET("products")
    fun getProductsList(): Call<List<ProductModelItem>>

    @GET("products/categories")
    fun getCategoriesList(): Call<CategoriesModel>

    @GET("products/category/{category}")
    fun getProductsListByCategory(@Path("category") category: String): Call<List<ProductModelItem>>

    @GET("products/category/{id}")
    fun getSingleProduct(@Path("id") category: String): Call<ProductModelItem>

}