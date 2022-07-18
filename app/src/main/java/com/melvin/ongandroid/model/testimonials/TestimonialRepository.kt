package com.melvin.ongandroid.model.testimonials

import android.provider.ContactsContract
import com.melvin.ongandroid.services.ApiClient
import com.melvin.ongandroid.services.TestimonialsService
import javax.inject.Inject

class TestimonialRepository
@Inject constructor(
    private val testimonialsService: TestimonialsService
) {
    //This function contains the testimonials list.
    //Automatically filter the items with an null id
    //If success is false it response with an empty list
    suspend fun getAllTestimonials(): List<DataModel> {
        return if (testimonialsService.getTestimonials().success) {
            val list = testimonialsService.getTestimonials().data
            list.filter { it.id != null }
        } else {
            emptyList()
        }

    }
}