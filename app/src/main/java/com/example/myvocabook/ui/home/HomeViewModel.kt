package com.example.myvocabook.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myvocabook.Vocabulary

class HomeViewModel : ViewModel() {

    private val selectedVoca = MutableLiveData<Vocabulary>()
    fun setVoca(word:String, meaning:String) {
        selectedVoca.value = Vocabulary(1,"day1",word, meaning, false)
    }
}