package com.melvin.ongandroid.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.melvin.ongandroid.businesslogic.*
import com.melvin.ongandroid.model.login.LoginPreferences
import com.melvin.ongandroid.model.signUpNewUser.NewUserBodyModel
import com.melvin.ongandroid.util.checkMail
import com.melvin.ongandroid.util.checkPassword
import com.melvin.ongandroid.util.checkPasswordLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class InputTypeLogIn { EMAIL, PASSWORD }
enum class InputTypeSignUp { NAME, EMAIL, PASSWORD, CONFIRM_PASSWORD }
data class FieldError<T>(val field: T, val isInvalid: Boolean, val message: String? = "")

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sendNewUserUseCase: SendNewUserUseCase,
    private val loginUseCase: GetLoginUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val loginPreferences: LoginPreferences,
    private val firebaseAuth: FirebaseAuth
) :
    ViewModel() {
    // Login
    private var _sessionStatus = MutableLiveData(Status.IDLE)
    val sessionStatus: LiveData<Status> = _sessionStatus
    private val _loginStatus = MutableLiveData(Status.IDLE)
    val loginStatus: LiveData<Status> = _loginStatus
    private val _statusButtonLogin = MutableLiveData(false)
    val statusButtonLogin: LiveData<Boolean> = _statusButtonLogin
    private var emailApplied: Boolean = false
    private var passwordApplied: Boolean = false
    private val _loginFormError = MutableLiveData<FieldError<InputTypeLogIn>>()
    val loginFormError: LiveData<FieldError<InputTypeLogIn>> = _loginFormError

    // sign up button status
    private val _statusButtonSignUp = MutableLiveData(false)
    val statusButtonSignUp: LiveData<Boolean> = _statusButtonSignUp
    //Correct fields flags
    private var _nameSignUpApplied = false
    private var _emailSignUpApplied = false
    private var _passwordSignUpApplied = false
    private var _confirmPasswordSignUpApplied = false
    private var _password: String = ""
    private var _confirmPassword: String = ""
    // sign up inputs status
    private val _signUpFormError = MutableLiveData<FieldError<InputTypeSignUp>>()
    val signUpFormError: LiveData<FieldError<InputTypeSignUp>> = _signUpFormError
    // sign up ConfirmPassword input status
    private val _passwordsMatch = MutableLiveData<FieldError<InputTypeSignUp>>()
    val passwordsMatch: LiveData<FieldError<InputTypeSignUp>> = _passwordsMatch
    private val _statusSignUpNewUser = MutableLiveData<Status>()
    val statusSignUpNewUser: LiveData<Status> = _statusSignUpNewUser

    fun checkSessionStatus() {
        //We check if there's a token and we validate it
        viewModelScope.launch {
            if (firebaseAuth.currentUser !== null) {
                _sessionStatus.value = Status.SUCCESS
                return@launch
            }
            val token = loginPreferences.getToken()
            if (token.isNullOrEmpty()) {
                //There's no session
                _sessionStatus.value = Status.ERROR
            } else {
                val getMeResponse = getMeUseCase(token)
                val isTokenValid = getMeResponse.success
                if (isTokenValid) {
                    _sessionStatus.value = Status.SUCCESS
                } else {
                    //The token expired so we clear the outdated data
                    _sessionStatus.value = Status.ERROR
                    loginPreferences.clear()
                }
            }
        }
    }



    fun onUserLogin(email: String, password: String, context: Context) {
        viewModelScope.launch {
            _loginStatus.value = Status.LOADING
            val loginResponse = loginUseCase(email, password)
            if (loginResponse.success) {
                val loginPreferences = LoginPreferences(context)
                val token = loginResponse.data?.data?.token ?: ""
                loginPreferences.saveToken(token)
                _loginStatus.value = Status.SUCCESS

            } else {
                val msg = "El email/contraseña es incorrecto"
                _loginFormError.postValue(FieldError(InputTypeLogIn.PASSWORD,true, msg))
                _loginStatus.value = Status.ERROR
            }
            _loginStatus.value = Status.IDLE
        }
    }

    // Validating email and password
    fun onLoginFieldChange(input: String, fieldType: InputTypeLogIn) {
        when (fieldType) {
            InputTypeLogIn.EMAIL -> {
                val isValid = input.checkMail()
                val isBlank = input.isBlank()
                val msg = if (isValid || isBlank) "" else "Email doesn't meet the condition"
                emailApplied = isValid
                _loginFormError.postValue(FieldError(
                    InputTypeLogIn.EMAIL,
                    !isValid && !isBlank,
                    msg,
                ))
            }
            InputTypeLogIn.PASSWORD -> {
                _password = input
                val isValid = input.checkPassword()
                val isBlank = input.isBlank()
                val msg = if (isValid || isBlank) "" else "Password doesn't meet the condition"
                passwordApplied = isValid
                _loginFormError.postValue(FieldError(
                    InputTypeLogIn.PASSWORD,
                    !isValid && !isBlank,
                    msg,
                ))
            }
        }
        _statusButtonLogin.postValue(emailApplied && passwordApplied)
    }



    // register new user in the data base
    fun sendNewUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            _statusSignUpNewUser.postValue(Status.LOADING)
            val response = sendNewUserUseCase(NewUserBodyModel(name, email, password))
            if (response.success) {
                _statusSignUpNewUser.postValue(Status.SUCCESS)
            } else {
                response.error?.let {
                    it.errors.email?.let { errors ->
                        _signUpFormError.postValue(
                            FieldError(
                                InputTypeSignUp.EMAIL,
                                true,
                                errors.first()
                            )
                        )
                    }
                    it.errors.name?.let { errors ->
                        _signUpFormError.postValue(
                            FieldError(
                                InputTypeSignUp.NAME,
                                true,
                                errors.first()
                            )
                        )
                    }
                    it.errors.password?.let { errors ->
                        _signUpFormError.postValue(
                            FieldError(
                                InputTypeSignUp.PASSWORD,
                                true,
                                errors.first()
                            )
                        )
                    }
                }
                _statusSignUpNewUser.postValue(Status.ERROR)
            }
        }
    }

    // Validating name, email, password and password == confirmPassword
    fun onSignupFieldChange(input: String, type: InputTypeSignUp) {
        when (type) {
            InputTypeSignUp.NAME -> {
                val isValid = input.isNotEmpty()
                val msg = if (isValid) "" else "Complete this field"
                _nameSignUpApplied = isValid
                _signUpFormError.postValue(FieldError(InputTypeSignUp.NAME, !isValid, msg))
            }
            InputTypeSignUp.EMAIL -> {
                val isValid = input.checkMail()
                val msg = if (isValid) "" else "Email doesn't meet the condition"
                _emailSignUpApplied = isValid
                _signUpFormError.postValue(FieldError(InputTypeSignUp.EMAIL, !isValid, msg))
            }
            InputTypeSignUp.PASSWORD -> {
                _password = input
                val isValid = input.checkPassword()
                val msg = if (isValid) "" else "Password doesn't meet the condition"
                _passwordSignUpApplied = isValid
                _signUpFormError.postValue(FieldError(InputTypeSignUp.PASSWORD, !isValid, msg))

                // check both passwords
                if (_confirmPassword != _password) {
                    _passwordsMatch.postValue(
                        FieldError(
                            InputTypeSignUp.CONFIRM_PASSWORD,
                            true,
                            "Passwords are not the same",
                        )
                    )
                    _confirmPasswordSignUpApplied = false
                } else {
                    _passwordsMatch.postValue(
                        FieldError(
                            InputTypeSignUp.CONFIRM_PASSWORD,
                            false,
                            ""
                        )
                    )
                    _confirmPasswordSignUpApplied = true
                }
            }
            InputTypeSignUp.CONFIRM_PASSWORD -> {
                _confirmPassword = input
                val isValid = _password == input
                val msg = if (isValid) "" else "Passwords are not the same"
                _confirmPasswordSignUpApplied = isValid
                _signUpFormError.postValue(
                    FieldError(
                        InputTypeSignUp.CONFIRM_PASSWORD,
                        !isValid,
                        msg,
                    )
                )
            }
        }

        _statusButtonSignUp.postValue(
            _nameSignUpApplied
                    && _emailSignUpApplied
                    && _passwordSignUpApplied
                    && _confirmPasswordSignUpApplied
        )
    }

    // Login



}