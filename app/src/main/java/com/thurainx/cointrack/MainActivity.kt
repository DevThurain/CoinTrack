package com.thurainx.cointrack

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.*
import cafe.adriel.voyager.navigator.Navigator
import com.thurainx.cointrack.presentation.coin_list.CoinListScreen
import com.thurainx.cointrack.presentation.map.MapScreen
import com.thurainx.cointrack.presentation.simple_bottom_navigation.SimpleNavigationScreen
import com.thurainx.cointrack.presentation.voyager_navigation.VoyagerStartScreen
import com.thurainx.cointrack.ui.theme.CoinTrackTheme
import com.thurainx.cointrack.worker.NotiWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val notiRequest = OneTimeWorkRequestBuilder<NotiWorker>()
//            .setConstraints(
//                Constraints.Builder()
//                    .setRequiredNetworkType(NetworkType.CONNECTED).build()
//            ).build()

        val notiRequest = PeriodicWorkRequestBuilder<NotiWorker>(repeatInterval = 15,TimeUnit.MINUTES).build()
        val workManager =  WorkManager.getInstance(applicationContext)



        setContent {
            CoinTrackTheme {
                val workInfo = workManager
                    .getWorkInfosForUniqueWorkLiveData("noti_work")
                    .observeAsState()
                    .value

                val notiInfo = remember(key1 =  workInfo) {
                    workInfo?.find { it.id == notiRequest.id }
                }

                LaunchedEffect(key1 = true, block = {
                    workManager.enqueueUniquePeriodicWork(
                        "noti_work",
                        ExistingPeriodicWorkPolicy.KEEP,
                        notiRequest,
                    )
                })

                when(notiInfo?.state){
                    WorkInfo.State.ENQUEUED -> Text(text = "Noti enqueued")
                    WorkInfo.State.BLOCKED -> Text(text = "Noti blocked")
                    WorkInfo.State.CANCELLED -> Text(text = "Noti cancelled")
                    WorkInfo.State.FAILED -> Text(text = "Noti failed")
                    WorkInfo.State.SUCCEEDED -> Text(text = "Noti success")
                    WorkInfo.State.RUNNING -> Text(text = "Noti running")
                }

//                val navController = rememberNavController()
//
//                NavHost(navController = navController, startDestination = "coin_list"){
//                    composable(route = "coin_list"){
//                        CoinListScreen(navController = navController)
//                    }
//                    composable(route = "coin_detail_screen/{coin_id}"){
//                        CoinDetailScreen()
//                    }
//                }

               // SimpleNavigationScreen()

               // Navigator(screen = CoinListScreen())
                Navigator(screen = MapScreen())
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