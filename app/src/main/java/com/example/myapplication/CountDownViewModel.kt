package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * View model for the Count Down timer display
 *
 * @author Lukas Boril
 * @version 2021.06.25
 */
class CountDownViewModel() : ViewModel() {
    var leftOverTime = MutableLiveData<Int>()

}