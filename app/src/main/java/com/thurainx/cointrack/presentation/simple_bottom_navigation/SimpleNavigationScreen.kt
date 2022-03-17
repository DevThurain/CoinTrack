package com.thurainx.cointrack.presentation.simple_bottom_navigation

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.thurainx.cointrack.common.Routes
import com.thurainx.cointrack.presentation.coin_list.CoinListScreen

@Composable
fun SimpleNavigationScreen(){
    val screens = listOf<NavigationItem>(
        NavigationItem.CoinListNavigationItem,
        NavigationItem.NotiNavigationItem,
        NavigationItem.SettingNavigationItem,
    )
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            CustomButtonNavigationBar(screens = screens, navController = navController)
        }
    ) {
      //  BuildNavigationGraph(navController = navController)
    }
}


@Composable
fun CustomButtonNavigationBar(screens : List<NavigationItem>,navController: NavController){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomAppBar(){
        screens.forEach{
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == it.route } == true,
                icon = { Icon(
                painter = painterResource(id = it.icon),
                contentDescription = ""
            )
            }, onClick = {
                navController.navigate(it.route) {

                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true

                }
            })
        }
    }
}

//@Composable
//fun BuildNavigationGraph(navController: NavHostController) {
//    NavHost(navController = navController, startDestination = Routes.CoinListScreen){
//        composable(route = Routes.CoinListScreen) {
//            CoinListScreen(navController = navController)
//        }
//        composable(route = Routes.NotiScreen) {
//            CoinListScreen(navController = navController)
//        }
//        composable(route = Routes.SettingScreen) {
//            CoinListScreen(navController = navController)
//        }
//    }
//}