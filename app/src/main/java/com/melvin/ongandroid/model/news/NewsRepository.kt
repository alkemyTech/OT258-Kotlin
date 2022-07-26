package com.melvin.ongandroid.model.news

import com.melvin.ongandroid.services.NewsService
import javax.inject.Inject

class NewsRepository
@Inject constructor(
    private val newsService: NewsService
) {
    //TODO: Pass the error message along (response.message on response.success: false)
    suspend fun getAllNews(): List<NewsModel> {
        val response = newsService.getNews()
        return if (response.success) {
            response.data
                .filter { it.id != -1 || it.id == null }
                .map { it.asNewsModel() }
        } else {
            emptyList()
        }
    }
}