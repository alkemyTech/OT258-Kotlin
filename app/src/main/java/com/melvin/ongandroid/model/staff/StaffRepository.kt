package com.melvin.ongandroid.model.staff

import com.melvin.ongandroid.services.StaffService
import com.melvin.ongandroid.services.TestimonialsService
import javax.inject.Inject

class StaffRepository
@Inject constructor(
    private val staffService: StaffService
) {
    //This function contains the staff list.
    //Automatically filter the items with an null id
    //If success is false it response with an empty list
    suspend fun getAllMembers(): List<StaffDataModel> {
        return if (staffService.getStaff().success) {
            val list = staffService.getStaff().data
            list.filter { it.id != null }
        } else {
            emptyList()
        }
    }
}