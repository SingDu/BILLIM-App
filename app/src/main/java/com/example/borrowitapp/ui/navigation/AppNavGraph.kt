// app/src/main/java/com/example/borrowitapp/ui/navigation/AppNavGraph.kt
package com.example.borrowitapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.borrowitapp.ui.home.HomeScreen
import com.example.borrowitapp.ui.map.MapTabScreen

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            BillmBottomBar(
                navController = navController,
                currentDestination = currentDestination
            )
        }
    ) { inner ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(inner)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onAddressClick = { /* TODO: 주소 선택 */ },
                    onSearchClick = { /* TODO: 검색 화면 */ }
                )
            }
            composable(Screen.Map.route) {
                MapTabScreen()
            }
            // 필요하면 나중에 추가
            composable(Screen.Post.route) { /* PostScreen() */ }
            composable(Screen.Message.route) { /* MessageScreen() */ }
            composable(Screen.My.route) { /* MyScreen() */ }
        }
    }
}

/** 하단 탭 이동을 단순화 */
internal fun navigateSingleTop(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
