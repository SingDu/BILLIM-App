package com.example.borrowitapp.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Colors = lightColorScheme(
    primary = BrandGreen,
    secondary = BrandTeal,
    onPrimary = Color.White,
    surface = Color.White,
    background = Color.White
)

@Composable
fun BorrowTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = Colors,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
