package com.inspire.techstore.db.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [History::class], version = 1, exportSchema = false)
@TypeConverters(HistoryProductsConverter::class)
abstract class HistoryDatabase: RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object{
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        fun getDatabase(context: Context): HistoryDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "history_database"
                ).build()
                INSTANCE = instance
                return  instance
            }
        }
    }

}