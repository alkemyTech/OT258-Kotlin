package com.melvin.ongandroid.businesslogic

import com.melvin.ongandroid.model.news.NewsRepository
import com.melvin.ongandroid.model.news.NewsResponse

class GetNewsUseCase (private val repository: NewsRepository)

{
    suspend operator fun invoke() : NewsResponse = repository.getAllNews()

}