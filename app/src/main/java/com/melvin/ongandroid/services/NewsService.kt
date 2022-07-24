package com.melvin.ongandroid.services

import com.melvin.ongandroid.model.news.NewsResponse
import com.melvin.ongandroid.services.di.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsService @Inject constructor( private val apiClient: ApiClient) {

    suspend fun getNews(): NewsResponse {
        return withContext(Dispatchers.IO) {
            apiClient.getNewsList().body() ?: NewsResponse(false, emptyList(), "")
        }
    }

}


