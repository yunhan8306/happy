package com.example.happy.presentation.detail

sealed interface DetailAction {
    object AddLike: DetailAction
    object Back: DetailAction
}