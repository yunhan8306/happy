package com.example.happy.common.config

import com.example.happy.model.AppSideEffect
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AppConfig {

    private val _appSideEffect = MutableSharedFlow<AppSideEffect>(extraBufferCapacity = 10)
    val appSideEffect = _appSideEffect.asSharedFlow()

    fun sendSideEffect(sideEffect: AppSideEffect) {
        _appSideEffect.tryEmit(sideEffect)
    }
}