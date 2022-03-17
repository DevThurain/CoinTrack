package com.thurainx.cointrack.presentation.coin_list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.thurainx.cointrack.presentation.coin_detail.CoinDetailScreen
import com.thurainx.cointrack.ui.theme.LightBlack

//@Composable
//fun CoinListScreen(
//    navController: NavController,
//    viewModel: CoinListViewModel = hiltViewModel()
//) {
//
//
//
//}


class CoinListScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val viewModel = getViewModel<CoinListViewModel>()
        val state = viewModel.state.value
        val navigator = LocalNavigator.currentOrThrow

        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = LightBlack)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(count = state.coins.size) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // navController.navigate("coin_detail_screen/${state.coins[it].id}")
                                       navigator.push(CoinDetailScreen(id = state.coins[it].id ?: ""))

                            },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = state.coins[it].name.toString(),
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                        Text(
                            text = if(state.coins[it].is_active == true) "Active" else "Inactive",
                            color = if(state.coins[it].is_active == true) Color.Green else Color.Red,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }

                }
            }

            if(state.error.isNotBlank()){
                Text(text = state.error, color = Color.Red, textAlign = TextAlign.Center ,modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center))
            }
            if(state.isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}