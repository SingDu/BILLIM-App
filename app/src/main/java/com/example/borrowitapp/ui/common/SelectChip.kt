package com.example.borrowitapp.ui.common

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun SelectChip(text: String, selected: Boolean, onClick: () -> Unit) {
    ElevatedFilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text) },
        elevation = FilterChipDefaults.elevatedFilterChipElevation(),
        shape = MaterialTheme.shapes.small
    )
}
