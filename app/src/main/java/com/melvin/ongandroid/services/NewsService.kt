package com.melvin.ongandroid.services

import com.melvin.ongandroid.model.news.NewsAPIResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsService @Inject constructor(
    private val retrofit: ApiClient
) {
    suspend fun getNews(): NewsAPIResponse {
        return withContext(Dispatchers.IO) {
            retrofit.getNewsList().body() ?: NewsAPIResponse()
        }
    }

}
