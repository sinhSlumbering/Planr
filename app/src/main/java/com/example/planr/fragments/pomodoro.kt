package com.example.planr.fragments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planr.components.pomodoroTimer

@Composable
fun pomodoro(){
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize(),
        ) {
        Box (
            contentAlignment = Alignment.Center,
            ){
            pomodoroTimer(
                totalTime = 100L * 1000L,
                handleColor = MaterialTheme.colors.primary,
                inactiveBarColor = MaterialTheme.colors.onBackground,
                activeBarColor =MaterialTheme.colors.primary,
                modifier = Modifier.size(200.dp),
            )
        }
    }
}