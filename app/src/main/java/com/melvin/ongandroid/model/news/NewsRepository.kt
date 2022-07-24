package com.melvin.ongandroid.model.news

import com.melvin.ongandroid.services.ApiClient
import com.melvin.ongandroid.services.NewsService
import javax.inject.Inject

class NewsRepository (
    private val api: NewsService)
    {

    suspend fun getAllNews() : NewsResponse {
    val response: NewsResponse = api.getNews()

        return response
    }


}

