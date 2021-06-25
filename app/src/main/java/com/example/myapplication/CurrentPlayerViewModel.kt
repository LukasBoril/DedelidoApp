package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentPlayerViewModel() : ViewModel() {
    var name = MutableLiveData<String>()

    init {
        name.value = "The next player is..."
    }
}