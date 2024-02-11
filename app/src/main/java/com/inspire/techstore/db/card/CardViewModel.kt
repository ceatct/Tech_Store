package com.inspire.techstore.db.card

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardViewModel (application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Card>>
    private val repository: CardRepository

    init {
        val cardDao = CardDatabase.getDatabase(application).cardDao()
        repository = CardRepository(cardDao)
        readAllData = repository.readAllData
    }

    fun addCard(card: Card){
        viewModelScope.launch(Dispatchers.IO){
            repository.addCard(card)
        }
    }

    fun deleteCard(card: Card){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCard(card)
        }
    }

}