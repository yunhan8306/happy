package com.example.happy.presentation.navigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.happy.common.util.safeLaunch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    app: Application,
): AndroidViewModel(app) {

    private val _navigationState = MutableStateFlow<NavigationType>(NavigationType.Search)
    val navigationState = _navigationState.asStateFlow()

    fun selectNavigation(menu: NavigationType) {
        viewModelScope.safeLaunch {
            _navigationState.value = menu
        }
    }
}