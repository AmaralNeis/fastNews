package com.fastnews.repository

import androidx.paging.PagingSource
import com.fastnews.service.model.PostData

class PostDataSource(val api: PostRepository, val isRefresh: Boolean) : PagingSource<String, PostData>() {


    override suspend fun load(params: LoadParams<String>): LoadResult<String, PostData> {

        val key = if (isRefresh) "" else params.key ?: ""

        val postResponse = api.getPosts(key, limit = 10)

        val result: MutableList<PostData> = mutableListOf()
        postResponse?.children?.map { postChildren -> result.add(postChildren.data) }

        return LoadResult.Page(
            data = result,
            prevKey = params.key,
            nextKey = postResponse?.after
        )
    }
}