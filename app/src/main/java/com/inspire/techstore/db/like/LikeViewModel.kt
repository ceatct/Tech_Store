package com.inspire.techstore.db.like

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LikeViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Like>>
    private val repository: LikeRepository

    init {
        val likeDao = LikeDatabase.getDatabase(application).likeDao()
        repository = LikeRepository(likeDao)
        readAllData = repository.readAllData
    }

    fun addLike(like: Like){
        viewModelScope.launch(Dispatchers.IO){
            repository.addLike(like)
        }
    }

    fun deleteLike(like: Like){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLike(like)
        }
    }

}