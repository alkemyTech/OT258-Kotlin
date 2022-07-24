package com.melvin.ongandroid.viewmodel

import android.widget.Toast
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

                if (!result.success) {
//                    newsList.postValue(result)
                    isLoading.postValue(false)

                }
            }
        }


        fun error() {
//            Toast.makeText(this, " Error!!, Ha ocurrido un error obteniendo la información...", Toast.LENGTH_SHORT).show()
        }




    }


