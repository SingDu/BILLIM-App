package com.example.borrowitapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.borrowitapp.ui.theme.*

@Composable
fun PrimaryButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Box(
        Modifier.fillMaxWidth().height(56.dp)
            .background(
                brush = Brush.horizontalGradient(listOf(BrandGreen, BrandTeal, BrandDeep)),
                shape = AppShapes.medium
            )
    ) {
        Button(
            onClick = onClick,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            shape = AppShapes.medium,
            modifier = Modifier.fillMaxSize()
        ) { Text(text, fontSize = 18.sp, color = Color.White) }
    }
}
