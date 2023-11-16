package com.example.planr.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planr.common.Result
import com.example.planr.data.model.Task
import com.example.planr.data.model.TaskProgress
import com.example.planr.data.repositories.TaskRepository
import com.example.planr.ui.events.ManageScreenUIEvents
import com.example.planr.ui.sideeffects.ManageScreenSideEffects
import com.example.planr.ui.states.ManageScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    private val _state: MutableStateFlow<ManageScreenUIState> = MutableStateFlow(ManageScreenUIState())
    val state: StateFlow<ManageScreenUIState> = _state.asStateFlow()

    private val _effect: Channel<ManageScreenSideEffects> = Channel()
    val effect = _effect.receiveAsFlow()

    fun sendEvent(event: ManageScreenUIEvents){
        reduce(event = event, oldState = state.value)
    }

    private fun setState(newState: ManageScreenUIState){
        _state.value = newState
    }

    private fun setEffect(builder: () -> ManageScreenSideEffects){
        val effectValue = builder()
        viewModelScope.launch {
            _effect.send(effectValue)
        }
    }
    fun fetchTasks() {
        viewModelScope.launch {
            try {
                when (val result = repository.getAllTasks()){
                    is Result.Failure ->{
                        TODO()
                    }
                    is Result.Success ->{
                        val tasks = result.data
                        _state.value = _state.value.copy(tasks = tasks, isLoading = false)
                    }

                }


            } catch (e: Exception) {
                TODO()
            }
        }
    }

    private fun reduce(event: ManageScreenUIEvents, oldState: ManageScreenUIState){
        when(event){
            is ManageScreenUIEvents.AddTask -> {
                addTask(
                    oldState = oldState,
                    title = event.title,
                    body = event.body,
                    startTime = event.startTime,
                    endTime = event.endTime,
                    )
            }
            is ManageScreenUIEvents.DeleteTask -> {
                deleteTask(
                    oldState = oldState,
                    id = event.id,
                )
            }
            ManageScreenUIEvents.GetTasks -> {
                getTasks(oldState = oldState)
            }
            is ManageScreenUIEvents.OnChangeAddTaskDialogState -> {
                onChangeAddTaskDialogState(oldState = oldState, isShown = event.show)
            }
            is ManageScreenUIEvents.OnChangeEndTime -> {
                onChangeEndTime(oldState = oldState, endTime = event.endTime)
            }
            is ManageScreenUIEvents.OnChangeStartTime -> {
                onChangeStartTime(oldState = oldState, startTime = event.startTime)
            }
            is ManageScreenUIEvents.OnChangeTaskBody -> {
                onChangeTaskBody(oldState = oldState, body = event.body)
            }
            is ManageScreenUIEvents.OnChangeTaskTitle -> {
                onChangeTaskTitle(oldState = oldState, title = event.title?:"")
            }
            is ManageScreenUIEvents.OnChangeUpdateDialogState -> {
                onUpdateAddTaskDialog(oldState, isShown = event.show)
            }
            is ManageScreenUIEvents.SetTaskToBeUpdated -> {
                setTaskToBeUpdated(oldState = oldState, task = event.taskToBeUpdated)
            }
            ManageScreenUIEvents.UpdateTask -> {
                updateTask(oldState = oldState)
            }

            is ManageScreenUIEvents.OnChangeDropDownExpanded -> {
                onChangeDropDownExpanded(oldState=oldState, expanded = event.expanded)
            }
            is ManageScreenUIEvents.OnChangeDropDownOption -> {
                onChangeDropDownOption(oldState=oldState, selected=event.progress)
            }
        }
    }

    private fun onChangeDropDownOption(oldState: ManageScreenUIState, selected: String) {
        setState(oldState.copy(selectedProgress = selected))
    }

    private fun onChangeDropDownExpanded(oldState: ManageScreenUIState, expanded: Boolean) {
        setState(oldState.copy(isProgressMenuExpanded = expanded))
    }

    private fun updateTask(oldState: ManageScreenUIState) {
        viewModelScope.launch {
            setState(oldState.copy(isLoading = true))

            val title = oldState.currentTextFieldTitle?:""
            val body = oldState.currentTextFieldBody?:""
            val startTime = oldState.currentTextFieldStartTime?:""
            val endTime = oldState.currentTextFieldEndTime?:""
            val progress = TaskProgress.valueOf(oldState.selectedProgress.toString())
            val taskToBeUpdated = oldState.taskToBeUpdated

            when(
                val result = repository.updateTask(
                    title = title,
                    body = body,
                    id = taskToBeUpdated?.id?:"",
                    endTime = endTime,
                    startTime = startTime,
                    progress = progress
                )
            ){
                is Result.Failure -> {
                    setState(oldState.copy(isLoading = false))
                    val errorMessage = result.exception.message?:"failed to update task"
                    setEffect { ManageScreenSideEffects.ShowSnackBarMessage(message = errorMessage) }
                }
                is Result.Success -> {
                    setState(
                        oldState.copy(
                            isLoading = false,
                            currentTextFieldTitle = null,
                            currentTextFieldBody = null,
//                            currentProgress = TaskProgress.UNSCHEDULED,
                            currentTextFieldEndTime = null,
                            currentTextFieldStartTime = null,
                            selectedProgress = null,
                        ),
                    )
                    sendEvent(ManageScreenUIEvents.OnChangeUpdateDialogState(false))
                    sendEvent(ManageScreenUIEvents.GetTasks)
                }
            }

        }
    }

    private fun setTaskToBeUpdated(oldState: ManageScreenUIState, task: Task) {
        setState(oldState.copy(taskToBeUpdated = task))
    }

    private fun onChangeEndTime(oldState: ManageScreenUIState, endTime: String) {
        setState(oldState.copy(currentTextFieldEndTime = endTime))
    }

    private fun onChangeStartTime(oldState: ManageScreenUIState, startTime: String) {
        setState(oldState.copy(currentTextFieldStartTime = startTime))
    }

    private fun onChangeTaskBody(oldState: ManageScreenUIState, body: String) {
        setState(oldState.copy(currentTextFieldBody = body))
    }
    private fun onChangeTaskTitle(oldState: ManageScreenUIState, title: String) {
        setState(oldState.copy(currentTextFieldTitle = title))
    }

    private fun onUpdateAddTaskDialog(oldState: ManageScreenUIState, isShown: Boolean) {
        setState(oldState.copy(isShowUpdateTaskDialog = isShown))
    }

    private fun onChangeAddTaskDialogState(oldState: ManageScreenUIState, isShown: Boolean) {
        setState(oldState.copy(isShowAddTaskDialog = isShown))
    }

    private fun getTasks(oldState: ManageScreenUIState) {
        viewModelScope.launch {
            setState(oldState.copy(isLoading = true))

            when (val result = repository.getAllTasks()){
                is Result.Failure ->{
                    setState(oldState.copy(isLoading = false))
                    val errorMessage = result.exception.message?:"failed to fetch tasks"
                    setEffect { ManageScreenSideEffects.ShowSnackBarMessage(message = errorMessage) }
                }
                is Result.Success -> {
                    val tasks = result.data
                    setState(oldState.copy(isLoading = false, tasks = tasks))
                }
            }
        }
    }

    private fun deleteTask(oldState: ManageScreenUIState, id: String) {
        viewModelScope.launch {
            setState(oldState.copy(isLoading = true))

            when(val result = repository.deleteTask(id = id)){
                is Result.Failure -> {
                    setState(oldState.copy(isLoading = false))

                    val errorMessage = result.exception.message?:"failed to delete task"
                    setEffect { ManageScreenSideEffects.ShowSnackBarMessage(message = errorMessage) }
                }
                is Result.Success -> {
                    setState(oldState.copy(isLoading = false))
                    sendEvent(ManageScreenUIEvents.GetTasks)
                    setEffect { ManageScreenSideEffects.ShowSnackBarMessage(message = "Task Deleted") }
                }
            }
        }
    }

    private fun addTask(oldState: ManageScreenUIState, title: String, body: String, startTime: String, endTime: String) {
        viewModelScope.launch {
            setState(
                oldState.copy(
                    isLoading = true
                )
            )

            when(val result = repository.addTask(title=title, body=body, startTime = startTime, endTime = endTime)){
                is Result.Failure -> {
                    setState(oldState.copy(isLoading = false))

                    val errorMessage = result.exception.message ?: "Error adding task"
                    setEffect { ManageScreenSideEffects.ShowSnackBarMessage(errorMessage) }
                }
                is Result.Success ->{
                    setState(
                        oldState.copy(
                            isLoading = false,
                            currentTextFieldTitle = "",
                            currentTextFieldBody = "",
                            currentTextFieldStartTime = "",
                            currentTextFieldEndTime = "",
                        ),
                    )
                    sendEvent(event = ManageScreenUIEvents.OnChangeAddTaskDialogState(show = false))
                    sendEvent(ManageScreenUIEvents.GetTasks)
                    setEffect { ManageScreenSideEffects.ShowSnackBarMessage("Task Added") }
                }
            }
        }
    }

}