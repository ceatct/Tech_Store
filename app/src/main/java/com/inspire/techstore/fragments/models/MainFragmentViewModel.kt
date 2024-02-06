package com.inspire.techstore.fragments.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inspire.techstore.api.NPointRetrofitInstance
import com.inspire.techstore.api.NPointServiceInterface
import com.inspire.techstore.api.RetroServiceInterface
import com.inspire.techstore.api.RetrofitInstance
import com.inspire.techstore.api.data.BannersModel
import com.inspire.techstore.api.data.CategoriesModel
import com.inspire.techstore.api.data.ProductModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragmentViewModel: ViewModel() {

    var liveDataList: MutableLiveData<List<ProductModelItem>?> = MutableLiveData()
    var liveDataListCategories: MutableLiveData<CategoriesModel?> = MutableLiveData()
    var liveDataListBanners: MutableLiveData<BannersModel?> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<List<ProductModelItem>?> {
        return liveDataList
    }

    fun getLiveDataObserverCategories(): MutableLiveData<CategoriesModel?> {
        return liveDataListCategories
    }

    fun getLiveDataObserverBanners(): MutableLiveData<BannersModel?> {
        return liveDataListBanners
    }

    fun makeAPICall() {
        val retroInstance =  RetrofitInstance. getRetrofitInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)
        val call = retroService.getProductsList()

        call.enqueue(object : Callback<List<ProductModelItem>> {

            override fun onFailure(call: Call<List<ProductModelItem>>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(
                call: Call<List<ProductModelItem>>,
                response: Response<List<ProductModelItem>>
            ) {
                liveDataList.postValue(response.body())
            }

        })

    }

    fun makeAPICallCategories() {
        val retroInstance =  RetrofitInstance. getRetrofitInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)
        val call = retroService.getCategoriesList()

        call.enqueue(object : Callback<CategoriesModel> {

            override fun onFailure(call: Call<CategoriesModel>, t: Throwable) {
                liveDataListCategories.postValue(null)
            }

            override fun onResponse(
                call: Call<CategoriesModel>,
                response: Response<CategoriesModel>
            ) {
                liveDataListCategories.postValue(response.body())
            }

        })

    }

    fun makeAPICallBanners() {
        val retroInstance =  NPointRetrofitInstance.getRetrofitInstance()
        val retroService = retroInstance.create(NPointServiceInterface::class.java)
        val call = retroService.getBanners()

        call.enqueue(object : Callback<BannersModel> {

            override fun onFailure(call: Call<BannersModel>, t: Throwable) {
                liveDataListBanners.postValue(null)
            }

            override fun onResponse(
                call: Call<BannersModel>,
                response: Response<BannersModel>
            ) {
                liveDataListBanners.postValue(response.body())
            }

        })

    }

}