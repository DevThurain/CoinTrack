package com.thurainx.cointrack.presentation.map

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import com.thurainx.cointrack.domain.model.Coin
import kotlinx.coroutines.flow.MutableStateFlow

class MapScreen() : Screen{
    @Composable
    override fun Content() {
        var uiSettings by remember { mutableStateOf(MapUiSettings()) }
        var properties by remember {
            mutableStateOf(MapProperties(isMyLocationEnabled = true))
        }
        val pathPoints by TestService.pathPoints.observeAsState()
        val liveInt by TestService.liveInt.observeAsState()
        var listLatLng by remember {
            mutableStateOf(listOf<LatLng>())
        }


        val paths by remember {
            mutableStateOf(pathPoints)
        }



//        if(pathPoints?.last()?.isNotEmpty() == true){
//            LaunchedEffect(key1 = pathPoints?.last(), block = {
//               pathPoints?.let {
//                    Log.d("final",it.last().joinToString(","))
////            }
//                }
//            })
//
//        }

//








        val context = LocalContext.current


        Box(Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                properties = properties,
                uiSettings = uiSettings
            ){
                if(pathPoints?.isNotEmpty() == true){
                    if(pathPoints?.last()?.size!! > 1){
                        val preLastLatLng = pathPoints?.last()!![pathPoints!!.last().size - 2]
                        val lastLatLng = pathPoints?.last()!!.last()
                        listLatLng = listLatLng + mutableListOf<LatLng>(preLastLatLng,lastLatLng)

                        Log.d("poly","produce poly")
                        Polyline(points = listLatLng)
                    }
                }


            }
            Switch(
                checked = uiSettings.zoomControlsEnabled,
                onCheckedChange = {
                    uiSettings = uiSettings.copy(zoomControlsEnabled = it)
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ){
                Button(onClick = {
                    context.startService(ACTION_START)
                    pathPoints?.let {
                        Log.d("final",it.toString())
                    }
                    Log.d("final",pathPoints.toString())
                }) {
                    Text(text = "Start")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    context.startService(ACTION_END)

                }) {
                        Text(text = pathPoints.toString())

                }
            }

        }
    }


    private fun Context.startService(action: String){
        val intent = Intent(this,TestService::class.java)
        intent.action = action
        this.startService(intent)
    }

}

