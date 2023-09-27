package com.example.planr.fragments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planr.components.pomodoroTimer

@Composable
fun pomodoro(){
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        pomodoroTimer(
            totalTime = 100L * 1000L,
            handleColor = MaterialTheme.colors.secondary,
            inactiveBarColor = MaterialTheme.colors.onSurface,
            activeBarColor = MaterialTheme.colors.secondaryVariant,
            modifier = Modifier.size(200.dp),
        )
    }
}