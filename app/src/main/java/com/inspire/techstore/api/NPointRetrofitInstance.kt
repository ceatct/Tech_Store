package com.inspire.techstore.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NPointRetrofitInstance {

    companion object {
        private const val BASE_URL = "https://api.npoint.io/"

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

}