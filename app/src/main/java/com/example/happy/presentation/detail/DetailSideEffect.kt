package com.example.happy.presentation.detail

import com.example.happy.model.CollectionData

data class DetailState(
    val status: DetailStatus = DetailStatus.UnInit,
    val data: CollectionData = CollectionData(),
    val isLike: Boolean = false
) {
    companion object {
        val Init: DetailState = DetailState()
    }
}

sealed interface DetailStatus {
    object UnInit : DetailStatus
    object Success : DetailStatus
}

sealed interface DetailSideEffect {
    object Back: DetailSideEffect
    object GoLike: DetailSideEffect
}