package com.melvin.ongandroid.businesslogic

import com.melvin.ongandroid.model.signUpNewUser.NewUserBodyModel
import com.melvin.ongandroid.model.signUpNewUser.NewUserRepository
import javax.inject.Inject

class SendNewUserUseCase @Inject constructor(private val newUserRepository: NewUserRepository) {
    suspend operator fun invoke(newUser: NewUserBodyModel): Boolean{
        return newUserRepository.sendNewUser(newUser)
    }
}