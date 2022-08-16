package com.melvin.ongandroid.services


import com.melvin.ongandroid.model.activities.ActivitiesModel
import com.melvin.ongandroid.model.contact.ContactDataModel
import com.melvin.ongandroid.model.contact.ContactResponse
import com.melvin.ongandroid.model.login.LoginBody
import com.melvin.ongandroid.model.login.LoginResponse
import com.melvin.ongandroid.model.news.NewsAPIResponse
import com.melvin.ongandroid.model.signUpNewUser.NewUserBodyModel
import com.melvin.ongandroid.model.signUpNewUser.NewUserResponse
import com.melvin.ongandroid.model.slides.SlidesModel
import com.melvin.ongandroid.model.staff.StaffModel
import com.melvin.ongandroid.model.testimonials.TestimonialsModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiClient {
    //GET request slides from API
    @GET("slides")
    suspend fun getSliderList(): Response<SlidesModel>

    //GET request testimonials from API
    @GET("testimonials")
    suspend fun getTestimonyList(): Response<TestimonialsModel>

    //GET request members from API

    @GET("members")
    suspend fun getStaffList(): Response<StaffModel>

    //GET request news from API
    @GET("news")
    suspend fun getNewsList(): Response<NewsAPIResponse>

    //GET request activities from API
    @GET("activities")
    suspend fun getActivitiesList(): Response<ActivitiesModel>

    //  http://ongapi.alkemy.org/api/docs#/Auth/post_login
    @POST("login")
    suspend fun login(@Body loginBody: LoginBody): Response<LoginResponse>

    //POST new contact data in API
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("contacts")
    suspend fun sendContact(@Body contact: ContactDataModel): Response<ContactResponse>

    //POST new user in Data Base
    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json")
    @POST("register")
    suspend fun sendNewUser(@Body newUser: NewUserBodyModel): Response<NewUserResponse>
}
