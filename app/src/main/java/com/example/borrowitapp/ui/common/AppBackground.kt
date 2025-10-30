package com.example.borrowitapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.borrowitapp.ui.theme.*

@Composable
fun AppBackground(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(BgTop, BgMid, BgLow, BgMid)))
    ) {
        Box(
            Modifier.offset(x = (-60).dp, y = 120.dp)
                .size(220.dp).alpha(0.06f).blur(50.dp)
                .clip(RoundedCornerShape(110.dp))
                .background(Color(0xFF35CC6F))
        )
        Box(
            Modifier.offset(x = 100.dp, y = (-40).dp)
                .size(180.dp).alpha(0.08f).blur(40.dp)
                .clip(RoundedCornerShape(90.dp))
                .background(Color(0xFF2DD36F))
        )
    }
}
