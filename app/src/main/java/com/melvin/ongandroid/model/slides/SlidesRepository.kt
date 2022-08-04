package com.melvin.ongandroid.model.slides

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.melvin.ongandroid.services.SlidesService
import com.melvin.ongandroid.util.deleteHTML
import javax.inject.Inject

// Repository where I storage slides (data from the Slide API response) that I get from the SlidesService
class SlidesRepository
@Inject constructor(
    private val slidesService: SlidesService,
    private val firebaseAnalytics: FirebaseAnalytics
) {
    suspend fun getAllSlides(): List<SlidesDataModel> {
        val bundle = Bundle()
        return if (slidesService.getSlides().success) {
            val list = slidesService.getSlides().data
            bundle.putString("Slider", "Slider GET was successful")
            firebaseAnalytics.logEvent("slider_retrieve_success", bundle)
            list.filter { it.id != null }.forEach { it.description = it.description?.deleteHTML() }
            list
        } else {
            bundle.putString("Slider", "Slider GET failed")
            firebaseAnalytics.logEvent("slider_retrieve_error", bundle)
            emptyList()
        }
    }
}
