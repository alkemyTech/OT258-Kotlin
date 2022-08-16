package com.melvin.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.melvin.ongandroid.businesslogic.*
import com.melvin.ongandroid.model.APIResponse
import com.melvin.ongandroid.model.signUpNewUser.NewUserBodyModel
import com.melvin.ongandroid.model.signUpNewUser.NewUserResponse
import com.melvin.ongandroid.model.signUpNewUser.NewUserResponseError
import com.melvin.ongandroid.model.signUpNewUser.NewUserResponseErrorBody
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class AuthViewModelTest {
    @MockK
    private lateinit var loginUseCase: GetLoginUseCase

    @MockK
    private lateinit var sendNewUserUseCase: SendNewUserUseCase

    private lateinit var viewModel: AuthViewModel

    @get: Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = AuthViewModel(
            sendNewUserUseCase,
            loginUseCase
        )
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }


    @Test
    fun `When viewModel is send a new User at the first time, get all Users and set the first value` () = runTest {

        // Given
        val newUser = NewUserBodyModel("alkemy", "alkemy@gmail.com", "#1alkemyK")
        val newUserResponse = NewUserResponse(true, " ")
        coEvery { sendNewUserUseCase(newUser) } returns APIResponse(success = true, data = newUserResponse, null)

        // When
        viewModel.sendNewUser(newUser.nameNewUser,newUser.emailNewUser, newUser.passwordNewUser)

        // Then
        assert(viewModel.statusSignUpNewUser.value == Status.SUCCESS)

    }

    @Test
    fun `When the name is empty` () = runTest {

        // Given
        val newUser = NewUserBodyModel(" ", "alkemy@gmail.com", "#1alkemyK")
        val newUserResponseError = NewUserResponseError("Error", NewUserResponseErrorBody())
        coEvery { sendNewUserUseCase(newUser) } returns APIResponse(success = false, data = null, newUserResponseError)

        // When
        viewModel.sendNewUser(newUser.nameNewUser,newUser.emailNewUser, newUser.passwordNewUser)

        // Then
        assert(viewModel.statusSignUpNewUser.value == Status.ERROR)

    }


    @Test
    fun `When the email is invalid` () = runTest {

        // Given
        val newUser = NewUserBodyModel("alkemy", " ", "#1alkemyK")
        val newUserResponseError = NewUserResponseError("Error", NewUserResponseErrorBody())
        coEvery { sendNewUserUseCase(newUser) } returns APIResponse(success = false, data = null, newUserResponseError)

        // When
        viewModel.sendNewUser(newUser.nameNewUser,newUser.emailNewUser, newUser.passwordNewUser)

        // Then
        assert(viewModel.statusSignUpNewUser.value == Status.ERROR)

    }


    @Test
    fun `When the password is invalid` () = runTest {
        // Given
        val newUser = NewUserBodyModel("alkemy", "alkemy@gmail.com", " ")
        val newUserResponseError = NewUserResponseError("Error", NewUserResponseErrorBody())
        coEvery { sendNewUserUseCase(newUser) } returns APIResponse(success = false, data = null, newUserResponseError)

        // When
        viewModel.sendNewUser(newUser.nameNewUser,newUser.emailNewUser, newUser.passwordNewUser)

        // Then
        assert(viewModel.statusSignUpNewUser.value == Status.ERROR)

    }
}