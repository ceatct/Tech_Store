package com.inspire.techstore.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(item: ProductDBItem)

    @Query("SELECT * FROM cart ORDER BY id ASC")
    fun getAllItems(): LiveData<List<ProductDBItem>>

}