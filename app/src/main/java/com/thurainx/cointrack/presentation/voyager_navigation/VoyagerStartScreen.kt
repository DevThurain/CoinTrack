package com.thurainx.cointrack.presentation.voyager_navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import com.thurainx.cointrack.presentation.coin_list.CoinListScreen
import com.thurainx.cointrack.presentation.noti.NotiScreen
import com.thurainx.cointrack.presentation.setting.SettingScreen

class VoyagerStartScreen : Screen {

    @Composable
    override fun Content() {
        var currentIndex by remember { mutableStateOf(0) }
        val navigator = LocalNavigator.currentOrThrow

        Navigator(screen = CoinListScreen()) { navigator ->
            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        backgroundColor = Color.White
                    ) {
                        BottomNavigationItem(
                            selected = currentIndex == 0,
                            onClick = {
                                currentIndex = 0
                                navigator.replace(CoinListScreen())
                            },
                            unselectedContentColor = Color.Red,
                            selectedContentColor = Color.Black,
                            icon = { FaIcon(faIcon = FaIcons.Bandcamp, size = 24.dp) }
                        )
                        BottomNavigationItem(
                            selected = currentIndex == 1,
                            onClick = {
                                currentIndex = 1
                                navigator.replace(NotiScreen())
                            },
                            unselectedContentColor = Color.Black.copy(alpha = 0.1f),
                            selectedContentColor = Color.Black,
                            icon = { FaIcon(faIcon = FaIcons.Envelope, size = 24.dp) }
                        )
                        BottomNavigationItem(
                            selected = currentIndex == 2,
                            onClick = {
                                currentIndex = 2
                                navigator.replace(SettingScreen())
                            },
                            unselectedContentColor = Color.Black.copy(alpha = 0.1f),
                            selectedContentColor = Color.Black,
                            icon = { FaIcon(faIcon = FaIcons.Tools, size = 24.dp) }
                        )

                    }
                }
            ) {
                CurrentScreen()
            }
        }


    }

}