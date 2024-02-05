package com.inspire.techstore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Like::class], version = 1, exportSchema = false)
abstract class LikeDatabase: RoomDatabase() {

    abstract fun likeDao():LikeDao

    companion object{
        @Volatile
        private var INSTANCE: LikeDatabase? = null

        fun getDatabase(context: Context): LikeDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LikeDatabase::class.java,
                    "like_database"
                ).build()
                INSTANCE = instance
                return  instance
            }
        }
    }

}
