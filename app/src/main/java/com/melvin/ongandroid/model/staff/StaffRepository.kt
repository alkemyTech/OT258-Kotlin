package com.melvin.ongandroid.model.staff

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.melvin.ongandroid.services.StaffService
import com.melvin.ongandroid.util.deleteHTML
import javax.inject.Inject

class StaffRepository
@Inject constructor(
    private val staffService: StaffService,
    private val firebaseAnalytics: FirebaseAnalytics
) {
    //This function contains the staff list.
    //Automatically filter the items with an null id
    //If success is false it response with an empty list
    suspend fun getAllMembers(): List<StaffDataModel> {
        val bundle = Bundle()
        return if (staffService.getStaff().success) {
            val list = staffService.getStaff().data
            // This 2 lines will log members_retrieve_successful on firebase when the api CALL has been succefully >>>
            bundle.putString("Member", "Member GET was successful")
            firebaseAnalytics.logEvent("members_retrieve_success", bundle)
            // <<<
            list.filter { it.id != null }.forEach { it.description = it.description?.deleteHTML() }
            list
        } else {
            // This 2 lines will log members_retrieve_error on firebase when the api CALL has failed >>>
            bundle.putString("Member", "Member GET failed")
            firebaseAnalytics.logEvent("members_retrieve_error", bundle)
            // <<<
            emptyList()
        }
    }
}