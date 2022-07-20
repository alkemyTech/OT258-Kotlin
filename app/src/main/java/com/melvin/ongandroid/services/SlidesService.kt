package com.melvin.ongandroid.services

import androidx.lifecycle.viewmodel.CreationExtras
import com.melvin.ongandroid.model.slides.SlidesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

// API request to get Slide response
class SlidesService @Inject constructor(private var apiClient: ApiClient) {
    suspend fun getSlides():SlidesModel{
        return withContext(Dispatchers.IO){
            apiClient.getSliderList().body() ?: SlidesModel()
        }
    }
}