package com.fastnews.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.fastnews.model.FastKeys


@Dao
interface KeysDao {
    @Insert(onConflict = REPLACE)
    suspend fun saveRedditKeys(fastKeys: FastKeys)

    @Query("SELECT * FROM fastKeys ORDER BY id DESC")
    suspend fun getKeys(): List<FastKeys>
}