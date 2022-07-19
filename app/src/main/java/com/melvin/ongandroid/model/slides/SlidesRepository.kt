package com.melvin.ongandroid.model.slides

import com.melvin.ongandroid.services.SlidesService
import javax.inject.Inject

// Repository where I storage slides (data from the Slide API response) that I get from the SlidesService
class SlidesRepository @Inject constructor(private val slidesService: SlidesService) {
    suspend fun getAllSlides(): List<SlidesDataModel>{
        return if (slidesService.getSlides().success){
            val list = slidesService.getSlides().data
            list.filter{it.id != null}
            } else {
                emptyList()
            }
    }
}
