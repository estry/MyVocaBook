package com.example.myvocabook.ui.web

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WebViewModel : ViewModel() {
    val selectedWord = MutableLiveData<String>()
    var currentState = MutableLiveData<Bundle>()
    fun setLiveData(word: String) {
        selectedWord.value = word
    }
    fun setBundle(bundle: Bundle){
        currentState.value = bundle
    }

    fun getBundle(): MutableLiveData<Bundle> {
        return currentState
    }
}