package com.inspire.techstore.db.card

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_table")
data class Card(
    @PrimaryKey(autoGenerate = true)
    val number: Long,
    val date: String,
    val cvv: String,
    val holder: String,
    val company: String
)