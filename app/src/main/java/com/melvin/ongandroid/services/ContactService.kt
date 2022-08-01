package com.melvin.ongandroid.services

import com.melvin.ongandroid.model.contact.ContactDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//API request to POST contact response
class ContactService @Inject constructor(private val apiClient: ApiClient) {
    suspend fun sendContact(contact: ContactDataModel): Boolean {
        return withContext(Dispatchers.IO) {
            apiClient.sendContact(contact).body()?.success ?: false
        }
    }
}