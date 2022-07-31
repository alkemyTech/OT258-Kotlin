package com.melvin.ongandroid.model.contact

import com.melvin.ongandroid.services.ContactService
import javax.inject.Inject

class ContactRepository @Inject constructor(private val contactService: ContactService) {
    suspend fun sendContact(contact: ContactDataModel): Boolean {
        return contactService.sendContact(contact)
    }
}
