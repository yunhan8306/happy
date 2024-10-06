package com.example.happy.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun ConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(size = 10.dp))
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "삭제하시겠습니까?",
                fontSize = 30.sp
            )
            Box(modifier = Modifier.height(50.dp))
            Row {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .clickableNoRipple(onDismiss),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "취소",
                        fontSize = 15.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .background(color = Color.Black, shape = RoundedCornerShape(size = 10.dp))
                        .clickableNoRipple(onConfirm),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "삭제",
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}