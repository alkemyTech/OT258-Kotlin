package com.melvin.ongandroid.businesslogic

import com.melvin.ongandroid.model.news.NewsModel
import com.melvin.ongandroid.model.news.NewsRepository
import javax.inject.Inject


class GetNewsUseCase @Inject constructor (private val repository: NewsRepository) {

    suspend operator fun invoke(): List<NewsModel> = repository.getAllNews()


}