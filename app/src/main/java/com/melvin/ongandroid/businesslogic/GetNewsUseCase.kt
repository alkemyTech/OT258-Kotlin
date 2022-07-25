package com.melvin.ongandroid.businesslogic

import com.melvin.ongandroid.model.news.NewsModel
import com.melvin.ongandroid.model.news.NewsRepository

import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(): List<NewsModel> = newsRepository.getAllNews()
}