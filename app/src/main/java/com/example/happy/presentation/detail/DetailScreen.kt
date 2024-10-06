package com.example.happy.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.happy.R

@Composable
fun DetailScreen(
    state: DetailState,
    onAction: (DetailAction) -> Unit
) {
    val scroll = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(top = 12.dp, start = 12.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.clickableNoRipple { onAction.invoke(DetailAction.Back) },
            painter = painterResource(id = R.drawable.btn_back ),
            contentDescription = "back",
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.data.titleKor,
                fontSize = 25.sp
            )
        }
        Box(
            modifier = Modifier
                .background(if(state.isLike) Color.Yellow else Color.Black)
                .clickableNoRipple { onAction.invoke(DetailAction.AddLike) }
        ) {
            Text(
                text = "즐겨찾기",
                color = Color.White,
                fontSize = 15.sp
            )
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 80.dp)
            .fillMaxSize()
            .verticalScroll(scroll)
    ) {
        SubcomposeAsyncImage(
            model = state.data.mainUri,
            contentDescription = "collection image",
            modifier = Modifier.aspectRatio(1f),
            contentScale = ContentScale.Crop,
            loading = { ShimmerLoadingAnimation() },
            error = { ShimmerLoadingAnimation() }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "작가명"
            )
            Text(
                text = state.data.writerName
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "제작연도"
            )
            Text(
                text = state.data.madeYear
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "부문"
            )
            Text(
                text = state.data.category
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "규격"
            )
            Text(
                text = state.data.standard
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "수집년도"
            )
            Text(
                text = state.data.manageYear
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "재료 및 기법"
            )
            Text(
                text = state.data.technic
            )
        }
    }
}