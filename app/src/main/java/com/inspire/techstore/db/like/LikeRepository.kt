package com.inspire.techstore.db.like

import androidx.lifecycle.LiveData

class LikeRepository(private val likeDao: LikeDao) {

    val readAllData: LiveData<List<Like>> = likeDao.readAllData()

    suspend fun addLike(like: Like){
        likeDao.addLike(like)
    }

    suspend fun deleteLike(like: Like){
        likeDao.deleteLike(like)
    }

}