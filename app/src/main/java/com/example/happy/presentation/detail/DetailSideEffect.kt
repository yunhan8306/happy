package com.example.happy.presentation.detail

sealed interface DetailSideEffect {
    object Back: DetailSideEffect
}