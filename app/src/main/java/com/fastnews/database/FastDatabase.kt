package com.fastnews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fastnews.database.dao.KeysDao
import com.fastnews.database.dao.PostDao
import com.fastnews.model.*

@Database(
    entities = [PostData::class, FastKeys::class] ,
    version = 1,
    exportSchema = false
)
abstract class FastDatabase : RoomDatabase() {
    companion object {
        fun create(context: Context): FastDatabase {
            val databaseBuilder =
                Room.databaseBuilder(context, FastDatabase::class.java, "fast_news_.db")
            return databaseBuilder.build()
        }
    }

    abstract fun postsDao(): PostDao
    abstract fun keysDao(): KeysDao
}