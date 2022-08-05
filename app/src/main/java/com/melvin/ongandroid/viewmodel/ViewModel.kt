package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.melvin.ongandroid.businesslogic.*
import com.melvin.ongandroid.model.activities.ActivitiesDataModel
import com.melvin.ongandroid.model.contact.ContactDataModel
import com.melvin.ongandroid.model.news.NewsModel
import com.melvin.ongandroid.model.signUpNewUser.NewUserBodyModel
import com.melvin.ongandroid.model.slides.SlidesDataModel
import com.melvin.ongandroid.model.staff.StaffDataModel
import com.melvin.ongandroid.model.testimonials.DataModel
import com.melvin.ongandroid.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Status { LOADING, SUCCESS, ERROR, IDLE }
enum class Errors { TESTIMONIALS, NEWS, SLIDE, ALL }
enum class InputTypeLogIn {EMAIL, PASSWORD}
enum class InputTypeSignUp {NAME, EMAIL, PASSWORD, CONFIRMPASSWORD}

@HiltViewModel
class ViewModel @Inject constructor(
    private val getTestimonialsUseCase: GetTestimonialsUseCase,
    private val getSlidesUseCase: GetSlidesUseCase,
    private val getStaffUseCase: GetStaffUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase,
    private val getNewsUseCase: GetNewsUseCase,
    private val sendContactUsesCase: SendContactUsesCase,
    private val sendNewUserUseCase: SendNewUserUseCase
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
            if (result!!.isNotEmpty()) {
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
        var condition = (_contactName.value.toString().checkName() && _contactMail.value.toString()
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
    fun manageButtonLogin(input: String, type: InputTypeLogIn){
        when (type){
            InputTypeLogIn.EMAIL -> emailApplied = input.checkMail()
            InputTypeLogIn.PASSWORD -> passwordApplied = input.checkPasswordLogin()
        }
        _statusButtonLogin.postValue(emailApplied && passwordApplied)
    }

    private val _statusSignUpNewUser = MutableLiveData<Status>()
    val statusSignUpNewUser: LiveData<Status> = _statusSignUpNewUser

    // register new user in the data base
    fun sendNewUser(name: String, email: String, password: String){
        viewModelScope.launch {
            _statusSignUpNewUser.postValue(Status.LOADING)

            var success =  sendNewUserUseCase(NewUserBodyModel(name, email, password))

            if (success){
                _statusSignUpNewUser.postValue(Status.SUCCESS)
            } else {
                //TODO c:
                _statusSignUpNewUser.postValue(Status.ERROR)
            }
        }
    }

    // status sign up button
    private val _statusButtonSignUp = MutableLiveData<Boolean>(false)
    val statusButtonSignUp: LiveData<Boolean> = _statusButtonSignUp
    // name new User
    private var _nameSignUpApplied = MutableLiveData<Boolean>(false)
    var nameSignUpApplied: LiveData<Boolean> = _nameSignUpApplied
    // email newUser
    private var _emailSignUpApplied = MutableLiveData<Boolean>(false)
    var emailSignUpApplied: LiveData<Boolean> = _emailSignUpApplied
    // password new User
    private var _passwordSignUpApplied = MutableLiveData<Boolean>(false)
    var passwordSignUpApplied: LiveData<Boolean> = _passwordSignUpApplied
    // confirm password new User
    private var _confirmPasswordSignUpApplied = MutableLiveData<Boolean>(false)
    var confirmPasswordSignUpApplied: LiveData<Boolean> = _confirmPasswordSignUpApplied
    private var password: String = ""

    // Validating name, email, password and password == confirmPassword
    fun manageButtonSignUp(input: String, type: InputTypeSignUp){

         if (type==InputTypeSignUp.PASSWORD){
             password = input
             _confirmPasswordSignUpApplied.postValue(false)
         }

        when (type){
            InputTypeSignUp.NAME -> _nameSignUpApplied.postValue(input.isNotEmpty())
            InputTypeSignUp.EMAIL -> _emailSignUpApplied.postValue(input.checkMail())
            InputTypeSignUp.PASSWORD -> _passwordSignUpApplied.postValue(input.checkPassword())
            InputTypeSignUp.CONFIRMPASSWORD -> _confirmPasswordSignUpApplied.postValue(password == input)
        }
        _statusButtonSignUp.postValue(_nameSignUpApplied.value!! && _emailSignUpApplied.value!! && _passwordSignUpApplied.value!! && _confirmPasswordSignUpApplied.value!!)
    }
}
