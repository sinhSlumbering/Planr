package com.example.planr.fragments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planr.components.AddTaskDialogComponent
import com.example.planr.components.EmptyComponent
import com.example.planr.components.LoadingComponent
import com.example.planr.components.TaskCardComponent
import com.example.planr.components.UpdateTaskDialogComponent
import com.example.planr.data.model.TaskProgress
import com.example.planr.ui.events.ManageScreenUIEvents
import com.example.planr.ui.sideeffects.ManageScreenSideEffects
import com.example.planr.viewmodels.ManageViewModel
import kotlinx.coroutines.flow.onEach


@Composable
fun ManageFragment(manViewModel: ManageViewModel){
    val uiState = manViewModel.state.collectAsState().value
    val effectFlow = manViewModel.effect
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = "fetchManageScreenData"){
        manViewModel.fetchTasks()
    }
    LaunchedEffect(key1 = "man-side-effect-key",){
        effectFlow.onEach { effect->
            when(effect){
                is ManageScreenSideEffects.ShowSnackBarMessage -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short,
                        actionLabel = "DISMISS",
                    )
                }
            }
        }
    }

    if(uiState.isShowAddTaskDialog){
        AddTaskDialogComponent(
            setTaskTitle = { title ->
                manViewModel.sendEvent(
                    event = ManageScreenUIEvents.OnChangeTaskTitle(title=title)
                )
            },
            setTaskBody = {body->
                manViewModel.sendEvent(
                    event = ManageScreenUIEvents.OnChangeTaskBody(body=body)
                )
            },
            setTaskStartTime = {startTime->
                manViewModel.sendEvent(
                    event = ManageScreenUIEvents.OnChangeStartTime(startTime= startTime)
                )
            },
            setTaskEndTime = {endTime->
                manViewModel.sendEvent(
                    event = ManageScreenUIEvents.OnChangeEndTime(endTime= endTime)
                )
            },
            saveTask = {
                       manViewModel.sendEvent(
                           event = ManageScreenUIEvents.AddTask(
                               title = uiState.currentTextFieldTitle?:"",
                               body = uiState.currentTextFieldBody?:"",
                               startTime = uiState.currentTextFieldStartTime?:"",
                               endTime = uiState.currentTextFieldEndTime?:"",
                               progress = TaskProgress.UNSCHEDULED
                           )
                       )
            },
            closeDialog = { manViewModel.sendEvent(
                event = ManageScreenUIEvents.OnChangeAddTaskDialogState(false)
            ) },
            uiState = uiState,
        )
    }
    if(uiState.isShowUpdateTaskDialog){
        UpdateTaskDialogComponent(
            setTaskTitle = { title ->
                manViewModel.sendEvent(
                    event = ManageScreenUIEvents.OnChangeTaskTitle(title=title)
                )
            },
            setTaskBody = {body->
                manViewModel.sendEvent(
                    event = ManageScreenUIEvents.OnChangeTaskBody(body=body?:"")
                )
            },
            setTaskStartTime = {startTime->
                manViewModel.sendEvent(
                    event = ManageScreenUIEvents.OnChangeStartTime(startTime= startTime?:"")
                )
            },
            setTaskEndTime = {endTime->
                manViewModel.sendEvent(
                    event = ManageScreenUIEvents.OnChangeEndTime(endTime= endTime?:"")
                )
            },
            saveTask = {
                manViewModel.sendEvent(event = ManageScreenUIEvents.UpdateTask)
                       },
            closeDialog = { manViewModel.sendEvent(event = ManageScreenUIEvents.OnChangeUpdateDialogState(false)) },
            onExpandedChange = {expanded->
                manViewModel.sendEvent(
                    event = ManageScreenUIEvents.OnChangeDropDownExpanded(expanded)
                )
            },
            selectedProgress = {str->
                manViewModel.sendEvent(
                    event = ManageScreenUIEvents.OnChangeDropDownOption(str?:"")
                )},
            task = uiState.taskToBeUpdated,
            uiState = uiState
        )
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        floatingActionButton = {
            Column {
                ExtendedFloatingActionButton(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.AddCircle,
                            contentDescription = "AddTask",
                        )

                    },
                    text = {
                        Text(text = "Add Task")
                    },
                    onClick = {
                              manViewModel.sendEvent(
                                  event = ManageScreenUIEvents.OnChangeAddTaskDialogState(show=true)
                              )
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical= 80.dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),

                )
            }
        }
    ){paddingValues ->
        Box(modifier = Modifier.padding(paddingValues=paddingValues)){
            when{
                uiState.isLoading ->{
                    LoadingComponent()
                }
                !uiState.isLoading && uiState.tasks.isNotEmpty() ->{
                    LazyColumn(contentPadding = PaddingValues(12.dp)){
                        items(uiState.tasks){task->
                            TaskCardComponent(
                                deleteTask = {id->
                                    manViewModel.sendEvent(
                                        event = ManageScreenUIEvents.DeleteTask(id=id)
                                    )
                                },
                                updateTask = {taskToBeUpdated->
                                    manViewModel.sendEvent(
                                        ManageScreenUIEvents.OnChangeUpdateDialogState(true)
                                    )

                                    manViewModel.sendEvent(
                                        event = ManageScreenUIEvents.SetTaskToBeUpdated(
                                            taskToBeUpdated = taskToBeUpdated
                                        )
                                    )
                                },
                                task = task,
                            )
                        }
                    }
                }
                !uiState.isLoading && uiState.tasks.isEmpty() -> {
                    EmptyComponent()
                }
            }
        }
    }
}