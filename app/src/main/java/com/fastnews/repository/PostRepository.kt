package com.fastnews.repository

import com.fastnews.service.api.RedditAPI
import com.fastnews.service.model.PostDataChild

object PostRepository : BaseRepository() {

    suspend fun getPosts(after: String, limit: Int): PostDataChild? {

        val postResponse = safeApiCall(call = { RedditAPI.redditService.getPostList(after, limit).await() },
            errorMessage = "Error to fetching posts")
        return postResponse?.data

    }
}