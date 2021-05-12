package com.example.myvocabook.ui.web

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WebViewModel: ViewModel( ){
    val selectedWord = MutableLiveData<String>()
    fun setLiveData(word:String){
        selectedWord.value = word
    }
}