package com.melvin.ongandroid.businesslogic

import com.melvin.ongandroid.model.APIResponse
import com.melvin.ongandroid.model.signUpNewUser.NewUserBodyModel
import com.melvin.ongandroid.model.signUpNewUser.NewUserRepository
import com.melvin.ongandroid.model.signUpNewUser.NewUserResponse
import com.melvin.ongandroid.model.signUpNewUser.NewUserResponseError
import javax.inject.Inject

class SendNewUserUseCase @Inject constructor(private val newUserRepository: NewUserRepository) {
    suspend operator fun invoke(newUser: NewUserBodyModel): APIResponse<NewUserResponse, NewUserResponseError>{
        return newUserRepository.sendNewUser(newUser)
    }
}