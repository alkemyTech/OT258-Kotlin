package com.melvin.ongandroid.services

import com.melvin.ongandroid.model.activities.ActivitiesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//API request to get Activities response
class ActivitiesService @Inject constructor(private val apiClient: ApiClient) {
    suspend fun getActivities(): ActivitiesModel {
        return withContext(Dispatchers.IO) {
            apiClient.getActivitiesList().body() ?: ActivitiesModel()
        }
    }
}
