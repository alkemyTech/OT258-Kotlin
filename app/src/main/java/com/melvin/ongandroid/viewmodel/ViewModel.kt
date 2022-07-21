package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.melvin.ongandroid.businesslogic.GetTestimonialsUseCase
import com.melvin.ongandroid.businesslogic.getSlidesUseCase
import com.melvin.ongandroid.model.slides.SlidesDataModel
import com.melvin.ongandroid.model.testimonials.DataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Status { LOADING, SUCCESS, ERROR }
enum class Errors { TESTIMONIALS, NEWS, SLIDE, ALL }

@HiltViewModel
class ViewModel @Inject constructor(
    private val getTestimonialsUseCase: GetTestimonialsUseCase,
    private val getSlidesUseCase: getSlidesUseCase,
) :
    ViewModel() {
    private val _testimonials = MutableLiveData<List<DataModel>>()
    val testimonials: LiveData<List<DataModel>> = _testimonials

    private val _testimonialStatus = MutableLiveData(Status.SUCCESS)
    val testimonialStatus: LiveData<Status> = _testimonialStatus

    private val _slideStatus = MutableLiveData(Status.SUCCESS)
    val slideStatus: LiveData<Status> = _slideStatus

    //change to Status.SUCCESS when news is implemented
    private val _newsStatus = MutableLiveData(Status.ERROR)
    val newsStatus: LiveData<Status> = _newsStatus


    private val _slidesModel = MutableLiveData<List<SlidesDataModel>>()
    val slidesModel: LiveData<List<SlidesDataModel>> = _slidesModel

    val slidesCallFailed = MutableLiveData<Boolean>()

    //val Mediator to observe States of apiCall response
    val apiStatus = MediatorLiveData<Errors>().apply {
        addSource(_testimonialStatus) {
            if (testimonialStatus.value == Status.ERROR &&
                slideStatus.value == Status.ERROR &&
                newsStatus.value == Status.ERROR
            ) {
                value = Errors.ALL
            } else if (_testimonialStatus.value == Status.ERROR) {
                value = Errors.TESTIMONIALS
            }
        }

        addSource(_slideStatus) {
            if (testimonialStatus.value == Status.ERROR &&
                slideStatus.value == Status.ERROR &&
                newsStatus.value == Status.ERROR
            ) {
                value = Errors.ALL
            } else if (_slideStatus.value == Status.ERROR) {
                value = Errors.SLIDE
            }
        }
        addSource(_newsStatus) {
            if (testimonialStatus.value == Status.ERROR &&
                slideStatus.value == Status.ERROR &&
                newsStatus.value == Status.ERROR
            ) {
                value = Errors.ALL
            } else if (_newsStatus.value == Status.ERROR) {
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
            var result = getSlidesUseCase()
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

    fun validateError(): Boolean = (testimonialStatus.value == Status.ERROR &&
            slideStatus.value == Status.ERROR &&
            newsStatus.value == Status.ERROR
            )

    //fun to reload apiCalls
    fun refresh() {
        onLoadTestimonials()
        onCreateSlides()
        // onLoadNews()
    }
}