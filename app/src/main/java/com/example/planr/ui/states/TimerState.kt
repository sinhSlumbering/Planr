package com.example.planr.ui.states

data class TimerState(
    val progress: Float,
    val currentTime: Long,
    val isTimerRunning: Boolean
)
