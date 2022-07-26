package com.melvin.ongandroid.businesslogic

import com.melvin.ongandroid.model.staff.StaffDataModel
import com.melvin.ongandroid.model.staff.StaffRepository
import com.melvin.ongandroid.model.testimonials.DataModel
import javax.inject.Inject

class GetStaffUseCase
@Inject constructor(
    private val staffRepository: StaffRepository
) {
    //This function calls the repository and get the Staff Data Model List
    suspend operator fun invoke(): List<StaffDataModel> = staffRepository.getAllMembers()
}