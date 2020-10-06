package com.fastnews.repository

import androidx.paging.PagingSource
import com.fastnews.service.api.RedditAPI
import com.fastnews.service.model.PostData

class PostDataSource(val api: PostRepository) : PagingSource<String, PostData>() {


    override suspend fun load(params: LoadParams<String>): LoadResult<String, PostData> {

        val postResponse = api.getPosts(params.key ?: "", limit = 10)

        val result: MutableList<PostData> = mutableListOf()
        postResponse?.children?.map { postChildren -> result.add(postChildren.data) }

        return LoadResult.Page(
            data = result,
            prevKey = params.key,
            nextKey = postResponse?.after
        )
    }
}


/*

class PostRepository(val api: BaseRepository) : PagingSource<String, PostData>() {


    override suspend fun load(params: LoadParams<String>): LoadResult<String, PostData> {

        val postResponse = api.safeApiCall(call = {
            RedditAPI.redditService.getPostList(after = params.key ?: "", limit = 5).await()
        }, errorMessage = "Error to fetching posts")

        val result: MutableList<PostData> = mutableListOf()
        postResponse?.data?.children?.map { postChildren -> result.add(postChildren.data) }

        return LoadResult.Page(
            data = result,
            prevKey = params.key,
            nextKey = postResponse?.data?.after
        )
    }
}
* */