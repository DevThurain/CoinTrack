package com.thurainx.cointrack.presentation.bottom_navigation

import com.thurainx.cointrack.R
import com.thurainx.cointrack.common.Routes

sealed class NavigationItem(val name: String,val icon: Int,val route: String){
    object CoinListNavigationItem : NavigationItem("Coin List", R.drawable.ic_launcher_foreground,Routes.CoinListScreen)
    object NotiNavigationItem : NavigationItem("Noti", R.drawable.ic_launcher_foreground,Routes.NotiScreen)
    object SettingNavigationItem : NavigationItem("Coin List", R.drawable.ic_launcher_foreground,Routes.SettingScreen)

}
