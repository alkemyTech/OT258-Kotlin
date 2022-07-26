package com.melvin.ongandroid.businesslogic

import com.melvin.ongandroid.model.activities.ActivitiesDataModel
import com.melvin.ongandroid.model.activities.ActivitiesRepository
import javax.inject.Inject

class GetActivitiesUseCase @Inject constructor(private val repository: ActivitiesRepository) {
    suspend operator fun invoke(): List<ActivitiesDataModel> = repository.getAllActivities()
}