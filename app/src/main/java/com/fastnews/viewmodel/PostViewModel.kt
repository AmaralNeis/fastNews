package com.fastnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fastnews.repository.PostDataSource
import com.fastnews.repository.PostRepository
import com.fastnews.service.model.PostData
import kotlinx.coroutines.flow.Flow

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    fun getPosts(isRefresh: Boolean): Flow<PagingData<PostData>> {
        return Pager(
                config = PagingConfig(pageSize = 10,
                    initialLoadSize = 20,
                    prefetchDistance = 5,
                    enablePlaceholders = false),
                pagingSourceFactory = { PostDataSource(repository, isRefresh) }
        ).flow.cachedIn(viewModelScope)
    }
}