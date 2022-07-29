package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.businesslogic.GetNewsUseCase
import com.melvin.ongandroid.model.news.NewsList
import kotlinx.coroutines.launch
import javax.inject.Inject

    class NewsViewModel @Inject constructor(
        private val getNewsUseCase: GetNewsUseCase
    ) : ViewModel() {

        val newsList: MutableLiveData<NewsList> = MutableLiveData()
        val isLoading = MutableLiveData<Boolean>()


        fun onCreate() {
            viewModelScope.launch {
                isLoading.postValue(true)
                val result = getNewsUseCase()

                if (!result.isNullOrEmpty()) {
//                newsList.postValue(result[0] )
                    isLoading.postValue(false)

                }
            }
        }

    }
