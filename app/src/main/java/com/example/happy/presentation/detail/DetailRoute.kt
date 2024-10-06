package com.example.happy.presentation.detail

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailRoute(
    viewModel: DetailViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as ComponentActivity
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest {
            when(it) {
                DetailSideEffect.Back -> {
                    activity.finish()
                }
            }
        }
    }

    state?.let {
        DetailScreen(
            state = it,
            onAction = { action ->
                when(action) {
                    DetailAction.Back -> {
                        activity.finish()
                    }
                    else -> viewModel.onAction(action)
                }
            }
        )
    } ?: run {
        activity.finish()
    }

    BackHandler {
        activity.finish()
    }
}