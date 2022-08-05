package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.businesslogic.GetLoginUseCase
import com.melvin.ongandroid.model.login.Login
import com.melvin.ongandroid.model.login.Preferences
import com.melvin.ongandroid.model.login.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val getLoginUseCase: GetLoginUseCase,
    private val preference: Preferences,
    val status: Status

):
    ViewModel()  {


    private val log = MutableLiveData<Token>()
     val login: LiveData<Token> = log

    private val logStatus = MutableLiveData(Status.SUCCESS)
    val loginStatus: LiveData<Status> = logStatus

    fun onLoadLogin(login: Login) = viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                getLoginUseCase.login(
                    login.email,
                    login.password
                )
            }
        if (result.isSuccessful()) {
            preference.saveToken("token")
        }
        log.value
//       log.value = result
    }

}


