package com.inspire.techstore.db.history

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.inspire.techstore.api.data.ProductModelItem

@Entity(tableName = "history_table")
data class History (
    @PrimaryKey(autoGenerate = true)
    val number: Int,
    val date: String,
    val status: String,
    val total: Double,
    val products: ArrayList<ProductModelItem>
)