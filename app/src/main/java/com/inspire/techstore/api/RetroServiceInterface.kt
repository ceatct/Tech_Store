package com.inspire.techstore.api

import com.inspire.techstore.api.data.Cart
import com.inspire.techstore.api.data.CartOutput
import com.inspire.techstore.api.data.CategoriesModel
import com.inspire.techstore.api.data.LoginModel
import com.inspire.techstore.api.data.ProductModelItem
import com.inspire.techstore.api.data.User
import com.inspire.techstore.api.data.UserCart
import com.inspire.techstore.api.data.UserTokenModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetroServiceInterface {
    @GET("products")
    fun getProductsList(): Call<List<ProductModelItem>>

    @GET("products/categories")
    fun getCategoriesList(): Call<CategoriesModel>

    @GET("products/category/{category}")
    fun getProductsListByCategory(@Path("category") category: String): Call<List<ProductModelItem>>

    @GET("products/{id}")
    fun getSingleProduct(@Path("id") id: String): Call<ProductModelItem>

    @GET("carts/5")
    fun getCart(): Call<UserCart>

    @POST("users")
    suspend fun registerUser(@Body user: User): Response<User>

    @POST("auth/login")
    suspend fun loginUser(@Body login: LoginModel): Response<UserTokenModel>

    @POST("carts")
    suspend fun addToCart(@Body cart: Cart): Response<CartOutput>

}