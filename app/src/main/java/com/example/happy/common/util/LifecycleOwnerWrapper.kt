package com.example.happy.common.util

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface LifecycleOwnerWrapper {

    fun initLifecycleOwner(): LifecycleOwner

    fun <T> Flow<T>.onResult(action: (T) -> Unit) {
        collect(initLifecycleOwner().lifecycleScope, action)
    }

    fun View.setFirstClickEvent(
        windowDuration: Long = 1000,
        onClick: () -> Unit,
    ) {
        clicks()
            .throttleFirst(windowDuration)
            .onEach { onClick.invoke() }
            .launchIn(initLifecycleOwner().lifecycleScope)
    }
}