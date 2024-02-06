package com.inspire.techstore.api

import com.inspire.techstore.api.data.BannersModel
import retrofit2.Call
import retrofit2.http.GET

interface NPointServiceInterface {

    @GET("989f4f41a4d437fbcbfa")
    fun getBanners(): Call<BannersModel>

}