package com.fastnews.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.fastnews.model.PostData

@Dao
interface PostDao {

    @Insert(onConflict = REPLACE)
    suspend fun savePosts(posts: List<PostData>)

    @Query("SELECT * FROM postData")
    fun getPosts(): PagingSource<Int, PostData>
}