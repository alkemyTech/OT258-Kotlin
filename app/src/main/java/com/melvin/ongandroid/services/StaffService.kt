package com.melvin.ongandroid.services

import com.melvin.ongandroid.model.staff.StaffModel
import com.melvin.ongandroid.model.testimonials.TestimonialsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StaffService @Inject constructor(private val apiClient: ApiClient) {
    suspend fun getStaff(): StaffModel {
        return withContext(Dispatchers.IO) {
            apiClient.getStaffList().body() ?: StaffModel()
        }
    }
}