package com.inspire.techstore.db.like

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LikeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLike(like: Like)

    @Query("SELECT * FROM like_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Like>>

    @Delete
    suspend fun deleteLike(like: Like)

}