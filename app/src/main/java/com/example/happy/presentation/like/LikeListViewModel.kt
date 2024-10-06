package com.example.happy.presentation.like

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happy.domain.usecase.GetLikeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LikeListViewModel @Inject constructor(
    private val getLikeListUseCase: GetLikeListUseCase
) : ViewModel() {

    val likeList = getLikeListUseCase.likeList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )
}