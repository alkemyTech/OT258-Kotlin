package com.melvin.ongandroid.model.activities

import com.melvin.ongandroid.services.ActivitiesService
import javax.inject.Inject

//This function contains the Activities list. Automatically filter the items with an null id
//If success is false it response with an empty list
class ActivitiesRepository @Inject constructor(private val activitiesService: ActivitiesService) {
    suspend fun getAllActivities(): List<ActivitiesDataModel> {
        return if (activitiesService.getActivities().success) {
            val list = activitiesService.getActivities().data
            list.filter { it.id != null }
        } else
            emptyList()
    }
}
