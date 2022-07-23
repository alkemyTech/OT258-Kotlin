package com.melvin.ongandroid.businesslogic

class GetNewsUseCase (private val repository: NewsRepository)

{
    suspend operator fun invoke() : List<NewsResponse>? = repository.getAllNews()

}