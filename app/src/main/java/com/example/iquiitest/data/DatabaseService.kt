package com.example.iquiitest.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RedditImage::class), version = 2, exportSchema = false)
public abstract class DatabaseService : RoomDatabase() {

    abstract fun redditImageDAO(): RedditImageDAO

    companion object {
        @Volatile
        private var INSTANCE: DatabaseService? = null

        fun getDatabase(context: Context): DatabaseService {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseService::class.java,
                    "iquii_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}