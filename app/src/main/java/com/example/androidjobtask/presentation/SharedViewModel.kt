package com.example.androidjobtask.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val sharedPreferencesData = MutableLiveData<String>()
}