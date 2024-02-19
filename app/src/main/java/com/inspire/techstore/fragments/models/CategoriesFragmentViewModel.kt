package com.inspire.techstore.fragments.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspire.techstore.api.RetroServiceInterface
import com.inspire.techstore.api.RetrofitInstance
import com.inspire.techstore.api.data.CategoriesModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesFragmentViewModel: ViewModel()  {

    var liveDataListCategories: MutableLiveData<CategoriesModel?> = MutableLiveData()

    fun getLiveDataObserverCategories(): MutableLiveData<CategoriesModel?> {
        return liveDataListCategories
    }

    fun loadData() {
        viewModelScope.launch {
            makeAPICallCategories()
        }
    }

    private fun makeAPICallCategories() {
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

}