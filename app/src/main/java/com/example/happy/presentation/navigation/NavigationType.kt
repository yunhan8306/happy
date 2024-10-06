package com.example.happy.presentation.navigation

sealed interface NavigationType {
    object Search : NavigationType
    object Like : NavigationType
}