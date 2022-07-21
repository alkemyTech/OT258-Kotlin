package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.businesslogic.GetTestimonialsUseCase
import com.melvin.ongandroid.businesslogic.getSlidesUseCase
import com.melvin.ongandroid.model.slides.SlidesDataModel
import com.melvin.ongandroid.model.testimonials.DataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class TestimonialStatus { LOADING, SUCCESS, ERROR }

@HiltViewModel
class ViewModel @Inject constructor(private val getTestimonialsUseCase: GetTestimonialsUseCase, private val getSlidesUseCase: getSlidesUseCase) :
    ViewModel() {
    private val _testimonials = MutableLiveData<List<DataModel>>()
    val testimonials : LiveData<List<DataModel>> = _testimonials
    private val _testimonialStatus = MutableLiveData(TestimonialStatus.SUCCESS)
    val testimonialStatus: LiveData<TestimonialStatus> = _testimonialStatus

    val slidesModel = MutableLiveData<List<SlidesDataModel>>()
    val isLoading = MutableLiveData<Boolean>()
    val slidesCallFailed = MutableLiveData<Boolean>()


    //This function refresh the testimonials livedata value with the use case response.
    //In case of an API exception the list does not update and the status becomes ERROR
    fun onLoadTestimonials() {
        viewModelScope.launch {
            _testimonialStatus.postValue(TestimonialStatus.LOADING)
            val result = getTestimonialsUseCase()
            if (result.isNotEmpty()) {
                _testimonials.postValue(result)
                _testimonialStatus.postValue(TestimonialStatus.SUCCESS)
            } else {
                _testimonialStatus.postValue(TestimonialStatus.ERROR)
            }
        }
    }


    // Recover the slides list to be used with the MotableLiveData of Slides list
    fun onCreateSlides(){
        viewModelScope.launch {
            var result = getSlidesUseCase()
            if (result.isNotEmpty()){
                slidesModel.postValue(result)
                slidesCallFailed.postValue(false)
            } else {
                slidesCallFailed.postValue(true)
            }
        }
    }
}

