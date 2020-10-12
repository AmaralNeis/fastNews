package com.fastnews.repository

import com.fastnews.model.PostDataChild
import com.fastnews.service.api.RedditAPI

object PostRepository : BaseRepository() {

    suspend fun getPosts(after: String, limit: Int): PostDataChild? {

        val postResponse = safeApiCall(call = { RedditAPI.redditService.getPostList(after, limit).await() },
            errorMessage = "Error to fetching posts")
        return postResponse?.data
    }
}