package com.example.happy.presentation.detail

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailRoute(
    viewModel: DetailViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as ComponentActivity
    val state by viewModel.state.collectAsState()

    var isShowConfirmDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest {
            when(it) {
                DetailSideEffect.Back -> {
                    activity.finish()
                }
            }
        }
    }

    DetailScreen(
        state = state,
        onAction = { action ->
            when(action) {
                DetailAction.Back -> {
                    activity.finish()
                }
                DetailAction.AddLike -> {
                    if(state.isLike) {
                        isShowConfirmDialog = true
                    } else {
                        viewModel.onAction(action)
                    }
                }
            }
        }
    )

    if(isShowConfirmDialog) {
        ConfirmDialog(
            onConfirm = {
                isShowConfirmDialog = false
                viewModel.onAction(DetailAction.AddLike)
            },
            onDismiss = {
                isShowConfirmDialog = false
            }
        )
    }

    BackHandler {
        activity.finish()
    }
}