package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * View model for the current player display.
 * Only the name of the player is displayed
 *
 * @author Lukas Boril
 * @version 2021.06.25
 */
class CurrentPlayerViewModel() : ViewModel() {
    var name = MutableLiveData<String>()

    init {
        name.value = "The next player is..."
    }
}