package com.thurainx.cointrack.presentation.coin_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thurainx.cointrack.ui.theme.LightBlack
import javax.inject.Inject

@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailViewModel = hiltViewModel()
){

    val state = viewModel.state.value
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = LightBlack)){
        Column {
            Text(
                text = state.detail?.name.toString(),
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = state.detail?.description.toString(),
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
        if(state.error.isNotBlank()){
            Text(text = state.error, color = Color.Red, textAlign = TextAlign.Center ,modifier = Modifier.fillMaxWidth().align(Alignment.Center))
        }
        if(state.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}