package com.fastnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.fastnews.application.FastApp
import com.fastnews.database.FastDatabase
import com.fastnews.repository.PostRepository
import com.fastnews.model.PostData
import com.fastnews.repository.FastRemoteMediator
import kotlinx.coroutines.flow.Flow

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    private val database = FastDatabase.create(FastApp.context)

    @OptIn(ExperimentalPagingApi::class)
    fun getPosts(): Flow<PagingData<PostData>> {
        return Pager(
            config = PagingConfig(pageSize = 30, initialLoadSize = 40, prefetchDistance = 5, enablePlaceholders = false),
            remoteMediator = FastRemoteMediator(repository, database),
            pagingSourceFactory = {database.postsDao().getPosts()}
        ).flow
    }
}