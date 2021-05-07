package com.example.myvocabook.ui.words

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordsViewModel : ViewModel() {

    val selectedDay = MutableLiveData<String>()
    fun setLiveData(day:String){
        selectedDay.value = day
    }
}