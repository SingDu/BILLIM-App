// app/src/main/java/com/example/borrowitapp/ui/navigation/BottomBar.kt
package com.example.borrowitapp.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.example.borrowitapp.ui.theme.BrandGreen
import com.example.borrowitapp.ui.theme.TextMuted

data class BottomItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val isCenter: Boolean = false
)

private val bottomItems = listOf(
    BottomItem(Screen.Home.route, "홈", Icons.Filled.Home),
    BottomItem(Screen.Map.route, "동네지도", Icons.Filled.Place),
    BottomItem(Screen.Post.route, "글쓰기", Icons.Filled.Add, isCenter = true),
    BottomItem(Screen.Message.route, "채팅", Icons.Filled.Chat),
    BottomItem(Screen.My.route, "MY", Icons.Filled.Person)
)

@Composable
fun BillmBottomBar(
    navController: NavHostController,
    currentDestination: NavDestination?
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        bottomItems.forEach { item ->
            val selected = currentDestination?.route == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { navigateSingleTop(navController, item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BrandGreen,
                    selectedTextColor = BrandGreen,
                    unselectedIconColor = TextMuted,
                    unselectedTextColor = TextMuted,
                    indicatorColor = Color(0x1A22C55E) // 살짝 그린 느낌
                )
            )
        }
    }
}
