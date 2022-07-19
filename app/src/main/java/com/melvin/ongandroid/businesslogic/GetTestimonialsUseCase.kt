package com.melvin.ongandroid.businesslogic

import com.melvin.ongandroid.model.testimonials.DataModel
import com.melvin.ongandroid.model.testimonials.TestimonialRepository
import javax.inject.Inject

class GetTestimonialsUseCase
@Inject constructor(
    private val testimonialRepository: TestimonialRepository
) {
    //This function calls the repository and get the Data Model List
    suspend operator fun invoke(): List<DataModel> = testimonialRepository.getAllTestimonials()
}