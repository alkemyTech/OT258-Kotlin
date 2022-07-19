package com.melvin.ongandroid.viewmodel

import android.content.res.Resources
import android.graphics.Color.red
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.GetTestimonialsUseCase
import com.melvin.ongandroid.model.testimonials.DataModel
import com.melvin.ongandroid.model.testimonials.TestimonialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val getTestimonialsUseCase: GetTestimonialsUseCase) :
    ViewModel() {
    private lateinit var resources: Resources
    val testimonialModel = MutableLiveData<List<DataModel>>()
    //slideModel and NewModel they are not complete yet use list<String> for example
    val slideModel = MutableLiveData<List</*SlideDataModel*/String >>()
    val newsModel = MutableLiveData<List</*newsModel*/String>>()

    val isLoading = MutableLiveData<Boolean>()

    //This function refresh the testimonials livedata value with the use case response.
    //The "isLoading" value is waiting the query completion, can be use in a future for the implementation of the loading spinner
    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getTestimonialsUseCase()
            if (result.isNotEmpty()) {
                testimonialModel.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    //fun to validate the list of requested models from the API
    fun validate() = (testimonialModel.value.isNullOrEmpty() && slideModel.value.isNullOrEmpty() && newsModel.value.isNullOrEmpty())

    //fun to refresh view if all models empty
    fun refreshButton(){
        onCreate()
        //onCreateSlideModel()
        //onCreateNewsModel()
    }

}