package com.example.planr.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ForgotPassScreen(
    onClick: () -> Unit,
) {
    Text(
        modifier = Modifier.clickable { onClick() },
        text = "Recovered",
    )
}