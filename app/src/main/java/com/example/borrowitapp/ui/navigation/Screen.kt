// app/src/main/java/com/example/borrowitapp/ui/navigation/Screen.kt
package com.example.borrowitapp.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Map : Screen("map")
    data object Post : Screen("post")
    data object Message : Screen("message")
    data object My : Screen("my")
}
