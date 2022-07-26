package com.melvin.ongandroid.model.testimonials

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.melvin.ongandroid.services.TestimonialsService
import javax.inject.Inject

class TestimonialRepository
@Inject constructor(
    private val testimonialsService: TestimonialsService,
    private val firebaseAnalytics: FirebaseAnalytics
) {
    //This function contains the testimonials list.
    //Automatically filter the items with an null id
    //If success is false it response with an empty list
    suspend fun getAllTestimonials(): List<DataModel> {
        val bundle = Bundle()
        if (testimonialsService.getTestimonials().success) {
            val list = testimonialsService.getTestimonials().data
            bundle.putString("Testimonials","Testimonials GET was successful")
            firebaseAnalytics.logEvent("testimonials_retrieve_success",bundle)
            return list.filter { it.id != null }

        } else {
            bundle.putString("Testimonials","Testimonials GET failed")
            firebaseAnalytics.logEvent("testimonies_retrieve_error",bundle)
            return emptyList()
        }
    }
}