package com.example.happy.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happy.common.util.safeLaunch
import com.example.happy.model.CollectionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow<CollectionData?>(null)
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<DetailSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val collectionData = savedStateHandle.get<CollectionData>("data")

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.safeLaunch {
            collectionData?.let {
                _state.value = collectionData
            } ?: run {
                _sideEffect.emit(DetailSideEffect.Back)
            }
        }
    }

    fun onAction(action: DetailAction) {
        when(action) {
            DetailAction.AddLike -> {
                addLike()
            }
            else -> Unit
        }
    }

    private fun addLike() {
        // 즐겨 찾기 등록
        state.value
    }
}