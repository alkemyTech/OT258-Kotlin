package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.melvin.ongandroid.businesslogic.GetTestimonialsUseCase
import com.melvin.ongandroid.model.testimonials.DataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Status { LOADING, SUCCESS, ERROR }

@HiltViewModel
class ViewModel @Inject constructor(private val getTestimonialsUseCase: GetTestimonialsUseCase) :
    ViewModel() {
    private val _testimonials = MutableLiveData<List<DataModel>>()
    val testimonials: LiveData<List<DataModel>> = _testimonials
    private val _testimonialStatus = MutableLiveData(Status.SUCCESS)
    val testimonialStatus: LiveData<Status> = _testimonialStatus
    private val _slideStatus = MutableLiveData(Status.SUCCESS)
    val slideStatus: LiveData<Status> = _slideStatus
    private val _newsStatus = MutableLiveData(Status.SUCCESS)
    val newsStatus: LiveData<Status> = _newsStatus


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

    fun validateError(): Boolean = (testimonialStatus.value == Status.ERROR &&
            slideStatus.value == Status.ERROR &&
            newsStatus.value == Status.ERROR
            )

    //fun to reload apiCalls
    fun refresh() {
        onLoadTestimonials()
        //onLoadSlide()
       // onLoadNews()
    }
}
