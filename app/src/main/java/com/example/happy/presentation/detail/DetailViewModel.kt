package com.example.happy.presentation.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happy.common.util.safeLaunch
import com.example.happy.domain.usecase.AddLikeUseCase
import com.example.happy.domain.usecase.DeleteLikeUseCase
import com.example.happy.domain.usecase.GetLikeListUseCase
import com.example.happy.model.CollectionData
import com.example.happy.model.getIsLike
import com.example.happy.model.setId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addLikeUseCase: AddLikeUseCase,
    private val deleteLikeUseCase: DeleteLikeUseCase,
    private val getLikeListUseCase: GetLikeListUseCase
): ViewModel() {

    private val _state = MutableStateFlow(DetailState.Init)
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<DetailSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val collectionData = savedStateHandle.get<CollectionData>("data")

    private val likeList = getLikeListUseCase.likeList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.safeLaunch {
            likeList.collectLatest { likeList ->
                collectionData?.let { data ->
                    _state.value = _state.value.copy(
                        status = DetailStatus.Success,
                        data = likeList.setId(data),
                        isLike = likeList.getIsLike(data)
                    )
                } ?: run {
                    _sideEffect.emit(DetailSideEffect.Back)
                }
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
        viewModelScope.safeLaunch {
            if(_state.value.isLike) {
                deleteLikeUseCase.invoke(state.value.data)
            } else {
                addLikeUseCase.invoke(state.value.data)
            }
        }
    }
}