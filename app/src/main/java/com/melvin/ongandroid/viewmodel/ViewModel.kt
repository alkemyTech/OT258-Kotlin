package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.melvin.ongandroid.businesslogic.*

import com.melvin.ongandroid.model.activities.ActivitiesDataModel
import com.melvin.ongandroid.model.news.NewsModel
import com.melvin.ongandroid.model.slides.SlidesDataModel
import com.melvin.ongandroid.model.staff.StaffDataModel
import com.melvin.ongandroid.model.testimonials.DataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Status { LOADING, SUCCESS, ERROR }
enum class Errors { TESTIMONIALS, NEWS, SLIDE, ALL }

@HiltViewModel
class ViewModel @Inject constructor(
    private val getTestimonialsUseCase: GetTestimonialsUseCase,
    private val getSlidesUseCase: GetSlidesUseCase,
    private val getStaffUseCase: GetStaffUseCase,
    private val getNewsUseCase: GetNewsUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase,
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
}
