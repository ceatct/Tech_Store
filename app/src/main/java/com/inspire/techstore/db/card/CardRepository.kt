package com.inspire.techstore.db.card

import androidx.lifecycle.LiveData
class CardRepository(private val cardDao: CardDao) {

    val readAllData: LiveData<List<Card>> = cardDao.readAllData()

    suspend fun addCard(card:Card){
        cardDao.addCard(card)
    }

    suspend fun deleteCard(card:Card){
        cardDao.deleteCard(card)
    }

}