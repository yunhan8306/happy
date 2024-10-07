package com.example.happy.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): ViewModel() {

    private val _splashTime = MutableStateFlow(1500)
    val splashTime = _splashTime.asStateFlow()

    private val _isSplashFinished = MutableStateFlow(false)
    val isSplashFinished = _isSplashFinished.asStateFlow()

    private fun fetch() {
        viewModelScope.launch {
            while (_splashTime.value > 0) {
                delay(1)
                _splashTime.value -= 1
            }

            if(_splashTime.value == 0) {
                _isSplashFinished.value = true
            }
        }
    }

    init {
        fetch()
    }
}