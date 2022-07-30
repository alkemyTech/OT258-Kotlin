package com.melvin.ongandroid.services

import com.melvin.ongandroid.model.news.NewsAPIResponse
import com.melvin.ongandroid.model.news.NewsList
import com.melvin.ongandroid.model.news.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsService @Inject constructor  (
    private val retrofit: ApiClient
) {
    suspend fun getNews() : NewsAPIResponse {
        return withContext(Dispatchers.IO) {
            retrofit.getNewsList().body() ?: NewsAPIResponse()

        }
    }

}
