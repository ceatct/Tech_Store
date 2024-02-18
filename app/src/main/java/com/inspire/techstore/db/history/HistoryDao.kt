package com.inspire.techstore.db.history

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToHistory(history: History)

    @Query("SELECT * FROM history_table ORDER BY number ASC")
    fun readAllData(): LiveData<List<History>>

}