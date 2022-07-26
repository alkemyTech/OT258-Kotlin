package com.melvin.ongandroid.model.news

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.melvin.ongandroid.services.NewsService
import javax.inject.Inject

class NewsRepository
@Inject constructor(
    private val newsService: NewsService,
    private val firebaseAnalytics: FirebaseAnalytics
) {
    //TODO: Pass the error message along (response.message on response.success: false)
    suspend fun getAllNews(): List<NewsModel> {
        val bundle = Bundle()
        val response = newsService.getNews()
        return if (response.success) {
            bundle.putString("last_news", "Last news GET was successful")
            firebaseAnalytics.logEvent("last_news_retrieve_success", bundle)
            response.data
                .filter { it.id != -1 || it.id == null }
                .map { it.asNewsModel() }
        } else {
            bundle.putString("last_News","Last news GET failed")
            firebaseAnalytics.logEvent("last_news_retrieve_error",bundle)
            emptyList()
        }
    }
}