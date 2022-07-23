package com.melvin.ongandroid.services

import com.melvin.ongandroid.model.news.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsService @Inject constructor  (
    private val retrofit: ApiClient
) {
    suspend fun getNews() : NewsResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.getNewsList()
            response.body()!!
//                .body() ?: emptyList()
        }
    }
}
