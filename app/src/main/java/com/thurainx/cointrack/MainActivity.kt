package com.thurainx.cointrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thurainx.cointrack.common.Constant
import com.thurainx.cointrack.presentation.coin_detail.CoinDetailScreen
import com.thurainx.cointrack.presentation.coin_list.CoinListScreen
import com.thurainx.cointrack.ui.theme.CoinTrackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinTrackTheme {
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    Greeting("Android")
//                }
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "coin_list"){
                    composable(route = "coin_list"){
                        CoinListScreen(navController = navController)
                    }
                    composable(route = "coin_detail_screen/{coin_id}"){
                        CoinDetailScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoinTrackTheme {
        Greeting("Android")
    }
}