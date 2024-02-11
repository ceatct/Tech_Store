package com.inspire.techstore.db.like

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.inspire.techstore.api.data.Rating

class RatingConverter {

    @TypeConverter
    fun fromRating(rating: Rating): String {
        return Gson().toJson(rating)
    }

    @TypeConverter
    fun toRating(jsonString: String): Rating {
        return Gson().fromJson(jsonString, Rating::class.java)
    }
}