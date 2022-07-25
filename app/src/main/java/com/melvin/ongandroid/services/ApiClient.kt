package com.melvin.ongandroid.services

import com.melvin.ongandroid.model.news.NewsAPIResponse
import com.melvin.ongandroid.model.slides.SlidesModel
import com.melvin.ongandroid.model.staff.StaffModel
import com.melvin.ongandroid.model.testimonials.TestimonialsModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiClient {
      //GET request slides from API
      @GET("slides")
      suspend fun getSliderList(): Response<SlidesModel>

      //GET request testimonials from API
      @GET("testimonials")
      suspend fun getTestimonyList(): Response<TestimonialsModel>

      //GET request staff from API
      @GET("members")
      suspend fun getStaffList(): Response<StaffModel>

      //GET request news from API
     @GET("news")
     suspend fun getNewsList(): Response<NewsAPIResponse>
}
