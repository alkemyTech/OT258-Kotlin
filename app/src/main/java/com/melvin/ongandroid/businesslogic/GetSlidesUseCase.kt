package com.melvin.ongandroid.businesslogic

import com.melvin.ongandroid.model.slides.SlidesDataModel
import com.melvin.ongandroid.model.slides.SlidesRepository
import javax.inject.Inject

// This function get the slide Repository and return it to viewModel
class GetSlidesUseCase
@Inject constructor(
    private val slidesRepository: SlidesRepository
) {
    suspend operator fun invoke(): List<SlidesDataModel> = slidesRepository.getAllSlides()
}