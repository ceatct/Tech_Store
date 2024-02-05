package com.inspire.techstore.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.inspire.techstore.api.data.Rating

@Entity(tableName = "like_table")
data class Like(
    @PrimaryKey(autoGenerate = true)
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)