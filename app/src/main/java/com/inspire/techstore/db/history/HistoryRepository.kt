package com.inspire.techstore.db.history

import androidx.lifecycle.LiveData

class HistoryRepository(private val historyDao: HistoryDao) {

    val readAllData: LiveData<List<History>> = historyDao.readAllData()

    suspend fun addToHistory(history: History){
        historyDao.addToHistory(history)
    }

}