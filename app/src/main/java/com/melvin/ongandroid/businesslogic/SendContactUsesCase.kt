package com.melvin.ongandroid.businesslogic

import com.melvin.ongandroid.model.contact.ContactDataModel
import com.melvin.ongandroid.model.contact.ContactRepository
import javax.inject.Inject

class SendContactUsesCase @Inject constructor(private val repository: ContactRepository) {
    suspend operator fun invoke(contactDataModel: ContactDataModel): Boolean =
        repository.sendContact(contactDataModel)
}
