package com.example.myapplication

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController

class CountDownViewModel() : ViewModel() {
    var leftOverTime = MutableLiveData<Int>()

}