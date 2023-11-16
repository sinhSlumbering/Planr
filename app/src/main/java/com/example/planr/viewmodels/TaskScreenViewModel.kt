package com.example.planr.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planr.common.Result
import com.example.planr.data.model.Task
import com.example.planr.data.model.TaskProgress
import com.example.planr.data.repositories.TaskRepository
import com.example.planr.ui.events.TaskScreenUIEvents
import com.example.planr.ui.states.TaskScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewModel @Inject constructor(
    private val repository: TaskRepository
): ViewModel(){
    private val _state: MutableStateFlow<TaskScreenUIState> = MutableStateFlow(TaskScreenUIState())
    val state: StateFlow<TaskScreenUIState> = _state.asStateFlow()

    private fun setState(newState: TaskScreenUIState){
        _state.value = newState
    }
    fun sendEvent(event: TaskScreenUIEvents){
        reduce(event = event, oldState = state.value)
    }

    fun fetchTasks(){
        viewModelScope.launch {
            try {
                when (val result = repository.getAllTasks()) {
                    is Result.Failure -> {
                        TODO()
                    }

                    is Result.Success -> {
                        val tasks = result.data
                        _state.value = _state.value.copy(tasks = tasks, isLoading = false)
                    }

                }

            } catch (e: Exception) {
                TODO()
            }
        }
    }


    private fun reduce(event: TaskScreenUIEvents, oldState: TaskScreenUIState) {
        when(event){
            TaskScreenUIEvents.GetTasks -> {
                getTasks(oldState = oldState)
            }
            is TaskScreenUIEvents.OnChangeDropDownExpanded -> {
                onChangeDropDownExpanded(oldState=oldState, expanded = event.expanded)
            }
            is TaskScreenUIEvents.OnChangeDropDownOption -> {
                onChangeDropDownOption(oldState=oldState, selected=event.progress)
            }
            is TaskScreenUIEvents.OnChangeExpandedDialogState -> {
                onChangeExpandedDialogState(oldState=oldState, isShown = event.show)
            }
            TaskScreenUIEvents.UpdateTask -> {
                updateTask(oldState=oldState)
            }

            is TaskScreenUIEvents.SetTaskToBeExpanded -> {
                setTaskToBeExpanded(oldState = oldState, task = event.taskToBeExpanded)
            }
        }
    }

    private fun onChangeDropDownOption(oldState: TaskScreenUIState, selected: String) {
        setState(oldState.copy(selectedProgress = selected))
    }

    private fun onChangeDropDownExpanded(oldState: TaskScreenUIState, expanded: Boolean) {
        setState(oldState.copy(isProgressMenuExpanded = expanded))
    }

    private fun setTaskToBeExpanded(oldState: TaskScreenUIState, task: Task) {
        setState(oldState.copy(taskToBeExpanded = task))
    }

    private fun updateTask(oldState: TaskScreenUIState) {
        viewModelScope.launch {
            setState(oldState.copy(isLoading = true))
            val title = oldState.taskToBeExpanded?.title?:""
            val body = oldState.taskToBeExpanded?.body?:""
            val startTime = oldState.taskToBeExpanded?.startTime?:""
            val endTime = oldState.taskToBeExpanded?.endTime?:""
            val progress = TaskProgress.valueOf(oldState.selectedProgress?:TaskProgress.UNSCHEDULED.toString())
            val id = oldState.taskToBeExpanded?.id?:""

            when(
                val result = repository.updateTask(
                title= title,
                body = body,
                id = id,
                startTime = startTime,
                endTime = endTime,
                progress = progress,
                )
            ){
                is Result.Failure -> {
                    setState(oldState.copy(isLoading = false))
                }
                is Result.Success -> {
                    setState(
                        oldState.copy(
                            isLoading = false,
                            selectedProgress = null,
                        ),
                    )
                    sendEvent(TaskScreenUIEvents.OnChangeExpandedDialogState(false))
                    sendEvent(TaskScreenUIEvents.GetTasks)
                }
            }
        }
    }


    private fun onChangeExpandedDialogState(oldState: TaskScreenUIState, isShown: Boolean) {
            setState(oldState.copy(isShowExpandedCard = isShown))
    }

    private fun getTasks(oldState: TaskScreenUIState) {
        viewModelScope.launch {
            setState(oldState.copy(isLoading = true))

            when (val result = repository.getAllTasks()){
                is Result.Failure ->{
                    setState(oldState.copy(isLoading = false))
                }
                is Result.Success -> {
                    val tasks = result.data
                    setState(oldState.copy(isLoading = false, tasks = tasks))
                }
            }
        }
    }
}