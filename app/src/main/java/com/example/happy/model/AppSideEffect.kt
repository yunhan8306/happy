package com.example.happy.model

sealed interface AppSideEffect {
    object GoToNavLike : AppSideEffect
}