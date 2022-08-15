package com.melvin.ongandroid.viewmodel

import android.content.Context
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.melvin.ongandroid.businesslogic.*
import com.melvin.ongandroid.model.activities.ActivitiesDataModel
import com.melvin.ongandroid.model.contact.ContactDataModel
import com.melvin.ongandroid.model.login.*
import com.melvin.ongandroid.model.news.NewsModel
import com.melvin.ongandroid.model.signUpNewUser.NewUserBodyModel
import com.melvin.ongandroid.model.slides.SlidesDataModel
import com.melvin.ongandroid.model.staff.StaffDataModel
import com.melvin.ongandroid.model.testimonials.DataModel
import com.melvin.ongandroid.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class Status { LOADING, SUCCESS, ERROR, IDLE }
enum class Errors { TESTIMONIALS, NEWS, SLIDE, ALL }
enum class InputTypeLogIn { EMAIL, PASSWORD }
enum class InputTypeSignUp { NAME, EMAIL, PASSWORD, CONFIRMPASSWORD }
data class FieldError<T>(val field: T,val isInvalid: Boolean, val message: String? = "")

@HiltViewModel
class ViewModel @Inject constructor(
    private val getTestimonialsUseCase: GetTestimonialsUseCase,
    private val getSlidesUseCase: GetSlidesUseCase,
    private val getStaffUseCase: GetStaffUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase,
    private val getNewsUseCase: GetNewsUseCase,
    private val sendContactUsesCase: SendContactUsesCase,
    private val sendNewUserUseCase: SendNewUserUseCase,
    private val loginRepository: LoginRepository
) :
    ViewModel() {

    //Staff//
    private val _staff = MutableLiveData<List<StaffDataModel>>()
    val staff: LiveData<List<StaffDataModel>> = _staff

    private val _staffStatus = MutableLiveData(Status.SUCCESS)
    val staffStatus: LiveData<Status> = _staffStatus

    //Testimonials//
    private val _testimonials = MutableLiveData<List<DataModel>>()
    val testimonials: LiveData<List<DataModel>> = _testimonials

    private val _testimonialStatus = MutableLiveData(Status.SUCCESS)
    val testimonialStatus: LiveData<Status> = _testimonialStatus

    //Slides//
    private val _slideStatus = MutableLiveData(Status.SUCCESS)
    val slideStatus: LiveData<Status> = _slideStatus

    //change to Status.SUCCESS when news is implemented
    private val _newsStatus = MutableLiveData(Status.SUCCESS)
    val newsStatus: LiveData<Status> = _newsStatus

    private val _slidesModel = MutableLiveData<List<SlidesDataModel>>()
    val slidesModel: LiveData<List<SlidesDataModel>> = _slidesModel

    private val _news = MutableLiveData<List<NewsModel>>()
    val news: LiveData<List<NewsModel>> = _news

    val slidesCallFailed = MutableLiveData<Boolean>()

    private val _activities = MutableLiveData<List<ActivitiesDataModel>>()
    val activities: LiveData<List<ActivitiesDataModel>> = _activities

    private val _activitiesStatus = MutableLiveData<Status>()
    val activitiesStatus: LiveData<Status> = _activitiesStatus

    // Login
    private val log: MutableLiveData<GenericLogin<Token>> = MutableLiveData()
    val login: LiveData<GenericLogin<Token>> = log

    //val Mediator to observe States of apiCall response
    val apiStatus = MediatorLiveData<Errors>().apply {
        addSource(_testimonialStatus) {
            if (testimonialStatus.value == Status.ERROR &&
                slideStatus.value == Status.ERROR &&
                newsStatus.value == Status.ERROR &&
                staffStatus.value == Status.ERROR
            ) {
                value = Errors.ALL
            } else if (_testimonialStatus.value == Status.ERROR) {
                value = Errors.TESTIMONIALS
            }
        }
        addSource(_slideStatus) {
            if (testimonialStatus.value == Status.ERROR &&
                slideStatus.value == Status.ERROR &&
                newsStatus.value == Status.ERROR &&
                staffStatus.value == Status.ERROR
            ) {
                value = Errors.ALL
            } else if (_slideStatus.value == Status.ERROR) {
                value = Errors.SLIDE
            }
        }
        addSource(_newsStatus) {
            if (testimonialStatus.value == Status.ERROR &&
                slideStatus.value == Status.ERROR &&
                newsStatus.value == Status.ERROR &&
                staffStatus.value == Status.ERROR
            ) {
                value = Errors.ALL
            } else if (_newsStatus.value == Status.ERROR) {
                value = Errors.NEWS
            }
        }
        addSource(_staffStatus) {
            if (testimonialStatus.value == Status.ERROR &&
                slideStatus.value == Status.ERROR &&
                newsStatus.value == Status.ERROR &&
                staffStatus.value == Status.ERROR
            ) {
                value = Errors.ALL
            } else if (_staffStatus.value == Status.ERROR) {
                value = Errors.NEWS
            }
        }
    }

    //This function refresh the testimonials livedata value with the use case response.
    //In case of an API exception the list does not update and the status becomes ERROR

    fun onLoadTestimonials() {
        viewModelScope.launch {
            _testimonialStatus.postValue(Status.LOADING)
            val result = getTestimonialsUseCase()
            if (result.isNotEmpty()) {
                _testimonials.postValue(result)
                _testimonialStatus.postValue(Status.SUCCESS)
            } else {
                _testimonialStatus.postValue(Status.ERROR)
            }
        }
    }

    // Recover the slides list to be used with the MutableLiveData of Slides list
    fun onCreateSlides() {
        viewModelScope.launch {
            val result = getSlidesUseCase()
            if (result.isNotEmpty()) {
                _slidesModel.postValue(result)
                slidesCallFailed.postValue(false)
                _slideStatus.postValue(Status.SUCCESS)
            } else {
                slidesCallFailed.postValue(true)
                _slideStatus.postValue(Status.ERROR)
            }
        }
    }

    //this function retrieves the list of activities and sets activitiesStatus
    fun onLoadActivities() {
        viewModelScope.launch {
            _activitiesStatus.postValue(Status.LOADING)
            val result = getActivitiesUseCase()
            if (result.isNotEmpty()) {
                _activities.postValue(result)
                _activitiesStatus.postValue(Status.SUCCESS)
            } else
                _activitiesStatus.postValue(Status.ERROR)
        }
    }

    //This function refresh the testimonials livedata value with the use case response.
    //In case of an API exception the list does not update and the status becomes ERROR

    fun onCreateStaff() {
        viewModelScope.launch {
            _staffStatus.postValue(Status.LOADING)
            val result = getStaffUseCase()
            if (result.isNotEmpty()) {
                _staff.postValue(result)
                _staffStatus.postValue(Status.SUCCESS)
            } else {
                _staffStatus.postValue(Status.ERROR)
            }
        }
    }

    fun onLoadNews() {
        viewModelScope.launch {
            _newsStatus.value = Status.LOADING
            val result = getNewsUseCase()
            if (result.isNotEmpty()) {
                _news.value = result
                _newsStatus.value = Status.SUCCESS
            } else {
                _newsStatus.value = Status.ERROR
            }
        }
    }

    //fun to reload apiCalls
    fun refresh() {
        onLoadTestimonials()
        onCreateSlides()
        onCreateStaff()
        onLoadNews()
    }

    private val _contactName = MutableLiveData<String>()
    private val _contactMail = MutableLiveData<String>()
    private val _contactMessage = MutableLiveData<String>()

    private val _isButtonEnabled = MutableLiveData(false)
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    private val _sendContactStatus = MutableLiveData<Status>()
    val sendContactStatus: LiveData<Status> = _sendContactStatus

    //fun to set contact name
    fun setContactName(name: String) {
        _contactName.value = name
    }

    //fun to set mail
    fun setContactMail(mail: String) {
        _contactMail.value = mail
    }

    //fun to set message
    fun setContactMessage(message: String) {
        _contactMessage.value = message
    }

    //fun to validate that the fields are correctly completed
    fun validateDataContact() {
        val condition = (_contactName.value.toString().checkName() && _contactMail.value.toString()
            .checkMail() && _contactMessage.value.toString().checkMessage())
        _isButtonEnabled.postValue(condition)
    }

    //this function POST the new contact in API is POST and response success boolean
    fun sendContactDate() {
        val contact = ContactDataModel(
            0,
            _contactName.value.toString(),
            _contactMail.value.toString(),
            _contactMessage.value.toString()
        )
        viewModelScope.launch {
            _sendContactStatus.value = Status.LOADING
            val result = sendContactUsesCase(contact)
            if (result) {
                _sendContactStatus.value = Status.SUCCESS
                resetValues()
            } else {
                _sendContactStatus.value = Status.ERROR
            }
            _sendContactStatus.value = Status.IDLE
        }
    }

    //fun to reset values of livedata
    private fun resetValues() {
        _contactName.value = ""
        _contactMail.value = ""
        _contactMessage.value = ""
    }

    private val _statusButtonLogin = MutableLiveData<Boolean>(false)
    val statusButtonLogin: LiveData<Boolean> = _statusButtonLogin
    private var emailApplied: Boolean = false
    private var passwordApplied: Boolean = false

    // Validating email and password
    fun manageButtonLogin(input: String, type: InputTypeLogIn) {
        when (type) {
            InputTypeLogIn.EMAIL -> emailApplied = input.checkMail()
            InputTypeLogIn.PASSWORD -> passwordApplied = input.checkPasswordLogin()
        }
        _statusButtonLogin.postValue(emailApplied && passwordApplied)
    }

    private val _statusSignUpNewUser = MutableLiveData<Status>()
    val statusSignUpNewUser: LiveData<Status> = _statusSignUpNewUser

    // register new user in the data base
    fun sendNewUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            _statusSignUpNewUser.postValue(Status.LOADING)
            val response = sendNewUserUseCase(NewUserBodyModel(name, email, password))
            if (response.success) {
                _statusSignUpNewUser.postValue(Status.SUCCESS)
            } else {
                response.error?.let {
                    it.errors.email?.let{ errors ->
                        _signUpFormError.postValue(FieldError(InputTypeSignUp.EMAIL, true, errors.first()))
                    }
                    it.errors.name?.let { errors ->
                        _signUpFormError.postValue(FieldError(InputTypeSignUp.NAME, true, errors.first()))
                    }
                    it.errors.password?.let { errors ->
                        _signUpFormError.postValue(FieldError(InputTypeSignUp.PASSWORD, true, errors.first()))
                    }
                }
                _statusSignUpNewUser.postValue(Status.ERROR)
            }
        }
    }

    // sign up button status
    private val _statusButtonSignUp = MutableLiveData(false)
    val statusButtonSignUp: LiveData<Boolean> = _statusButtonSignUp

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

    // Validating name, email, password and password == confirmPassword
    fun onFieldChange(input: String, type: InputTypeSignUp) {
        when (type) {
            InputTypeSignUp.NAME -> {
                val isValid = input.isNotEmpty()
                val msg = if (isValid) "" else "Complete this field"
                _nameSignUpApplied = isValid
                _signUpFormError.postValue(FieldError(InputTypeSignUp.NAME,!isValid, msg))
            }
            InputTypeSignUp.EMAIL -> {
                val isValid = input.checkMail()
                val msg = if (isValid) "" else "Email doesn't meet the condition"
                _emailSignUpApplied = isValid
                _signUpFormError.postValue(FieldError(InputTypeSignUp.EMAIL,!isValid, msg))
            }
            InputTypeSignUp.PASSWORD -> {
                _password = input
                val isValid = input.checkPassword()
                val msg = if (isValid) "" else "Password doesn't meet the condition"
                _passwordSignUpApplied = isValid
                _signUpFormError.postValue(FieldError(InputTypeSignUp.PASSWORD,!isValid, msg,))

                // check both passwords
                if (_confirmPassword != _password){
                    _passwordsMatch.postValue(FieldError(InputTypeSignUp.CONFIRMPASSWORD, true, "Passwords are not the same",))
                    _confirmPasswordSignUpApplied = false
                } else {
                    _passwordsMatch.postValue(FieldError(InputTypeSignUp.CONFIRMPASSWORD, false, ""))
                    _confirmPasswordSignUpApplied = true
                }
            }
            InputTypeSignUp.CONFIRMPASSWORD -> {
                _confirmPassword = input
                val isValid = _password == input
                val msg = if (isValid) "" else "Passwords are not the same"
                _confirmPasswordSignUpApplied = isValid
                _signUpFormError.postValue(FieldError(InputTypeSignUp.CONFIRMPASSWORD,!isValid, msg,))
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

    fun onLoadLogin(login: Login, context: Context) = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) {
            loginRepository.login(
                login.email,
                login.password
            )
        }
        if (result.isSuccessful()) {
            val preferences = Preferences(context)
            preferences.saveToken("")
        } else {

        }
        log.value = result

    }
}


