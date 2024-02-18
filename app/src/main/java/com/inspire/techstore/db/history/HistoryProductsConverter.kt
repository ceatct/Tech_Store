package com.inspire.techstore.db.history

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.inspire.techstore.api.data.ProductModelItem

class HistoryProductsConverter {

    @TypeConverter
    fun fromList(products: ArrayList<ProductModelItem>): String {
        return Gson().toJson(products)
    }

    @TypeConverter
    fun toList(data: String): ArrayList<ProductModelItem> {
        return Gson().fromJson(data, object : TypeToken<ArrayList<ProductModelItem>>() {}.type)
    }
}
