package com.melvin.ongandroid.services

import com.melvin.ongandroid.model.news.NewsAPIResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsService @Inject constructor(
    private val apiClient: ApiClient
) {
    suspend fun getNews(): NewsAPIResponse {
        return withContext(Dispatchers.IO) {
            apiClient.getNewsList().body() ?: NewsAPIResponse()
        }
    }
}