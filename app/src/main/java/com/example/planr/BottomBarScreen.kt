package com.example.planr

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PunchClock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val route: String,
    val title: String,
    val icon: ImageVector,
){
    object Home: BottomBarScreen(
        route = "HOME",
        title = "HOME",
        icon = Icons.Default.Home
    )
    object Pomodoro: BottomBarScreen(
        route = "POMODORO",
        title = "POMODORO",
        icon = Icons.Default.PunchClock
    )
    object Settings: BottomBarScreen(
        route = "Settings",
        title = "Settings",
        icon = Icons.Default.Settings
    )

}