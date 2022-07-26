package com.melvin.ongandroid.services

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.melvin.ongandroid.model.testimonials.TestimonialsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

class TestimonialsService @Inject constructor(
    private val apiClient: ApiClient,
) {
    //This function get the response from the API and get the StaffModel.
    //If this is null or the success is false it will response with an empty object
    suspend fun getTestimonials(): TestimonialsModel {
        return withContext(Dispatchers.IO) {
            apiClient.getTestimonyList().body() ?: TestimonialsModel()
        }
    }
}