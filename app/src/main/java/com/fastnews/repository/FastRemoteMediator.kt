package com.fastnews.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.fastnews.database.FastDatabase
import com.fastnews.model.FastKeys
import com.fastnews.model.PostData
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class FastRemoteMediator (private val redditService: PostRepository,
                          private val redditDatabase: FastDatabase) : RemoteMediator<Int, PostData>() {
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, PostData>): MediatorResult {

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    getRedditKeys()
                }
            }

            val response = redditService.getPosts(after = loadKey?.after ?: "", limit = state.config.pageSize)


            val posts = mutableListOf<PostData>()
            response?.children?.forEach {
                with(it.data) {
                    val postData = PostData(
                        id = id,
                        author = author,
                        thumbnail = thumbnail,
                        name = name,
                        numComments = numComments,
                        score = score,
                        title = title,
                        createdUtc = createdUtc,
                        url = url,
                        imageUrl = preview?.images?.first()?.source?.url
                    )

                    posts.add(postData)
                }

            }

            if (posts.isNotEmpty()) {
                redditDatabase.withTransaction {
                    redditDatabase.keysDao().saveRedditKeys(FastKeys(0, response?.after))
                    redditDatabase.postsDao().savePosts(posts)
                }

            }
            MediatorResult.Success(endOfPaginationReached = response?.after == null)

        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRedditKeys(): FastKeys? {
        return redditDatabase.keysDao().getKeys().firstOrNull()

    }

}