package com.inspire.techstore.db.card

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCard(card:Card)

    @Query("SELECT * FROM card_table ORDER BY number ASC")
    fun readAllData(): LiveData<List<Card>>

    @Delete
    suspend fun deleteCard(card:Card)

}