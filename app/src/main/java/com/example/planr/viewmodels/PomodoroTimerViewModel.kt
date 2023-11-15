package com.example.planr.viewmodels
//
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.planr.ui.states.TimerState
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//
//@HiltViewModel
//class PomodoroTimerViewModel : ViewModel() {
//    // State variables
//    private var totalTime: Long = 0L
//    private var currentTime: Long = 0L
//    private var isTimerRunning: Boolean = false
//
//    // Exposed LiveData for observing changes
//    val timerState = mutableStateOf(TimerState(0f, 0L, false))
//
//    // Function to start/stop the timer
//    fun toggleTimer() {
//        viewModelScope.launch {
//            isTimerRunning = !isTimerRunning
//            while (currentTime > 0L && isTimerRunning) {
//                delay(100L)
//                currentTime -= 100L
//                updateTimerState()
//            }
//        }
//    }
//
//    // Function to reset the timer
//    fun resetTimer() {
//        currentTime = totalTime
//        isTimerRunning = false
//        updateTimerState()
//    }
//
//    // Function to update the timer state
//    private fun updateTimerState() {
//        val progress = if (totalTime > 0) (1 - currentTime.toFloat() / totalTime.toFloat()) else 0f
//        timerState.value = TimerState(progress, currentTime, isTimerRunning)
//    }
//
//    // Function to initialize the timer with total time
//    fun initializeTimer(totalTime: Long) {
//        this.totalTime = totalTime
//        this.currentTime = totalTime
//        resetTimer() // Reset the timer initially
//    }
//}