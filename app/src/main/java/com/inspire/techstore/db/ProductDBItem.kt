package com.inspire.techstore.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.inspire.techstore.api.data.Rating

@Entity (tableName = "cart")
data class ProductDBItem (
    @PrimaryKey(autoGenerate = true)
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String,
    val count: Int
)