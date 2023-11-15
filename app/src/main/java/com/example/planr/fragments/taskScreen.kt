package com.example.planr.fragments

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planr.components.EmptyComponent
import com.example.planr.components.ExpandedTaskCard
import com.example.planr.components.LoadingComponent
import com.example.planr.components.profileHeaderComponent
import com.example.planr.components.startScreenGreeting
import com.example.planr.components.taskComponent
import com.example.planr.ui.events.TaskScreenUIEvents
import com.example.planr.viewmodels.TaskScreenViewModel

@Composable
fun taskScreen(
    tsViewModel: TaskScreenViewModel,
    onClickProfile: () ->Unit,
) {
    val uiState = tsViewModel.state.collectAsState().value
    LaunchedEffect(key1 = "fetchTaskScreenData",){
        tsViewModel.fetchTasks()
    }
    if(uiState.isShowExpandedCard){
        ExpandedTaskCard(
            task = uiState.taskToBeExpanded,
            closeDialog = {
                tsViewModel.sendEvent(
                    event = TaskScreenUIEvents.OnChangeExpandedDialogState(false)
                )
            },
            uiState = uiState,
            onExpandedChange = {},
        )
    }
    when{
        uiState.isLoading->{
            LoadingComponent()
        }
        !uiState.isLoading && uiState.tasks.isNotEmpty()->{
            LazyColumn(
                contentPadding = PaddingValues(
                    start=16.dp,
                    top = 16.dp,
                    bottom = 16.dp,
                )
            ){
                item{
                    profileHeaderComponent(
                        onClickProfile = { onClickProfile() }
                    )
                }
                item{
                    Spacer(modifier = Modifier.height(30.dp))

                    startScreenGreeting()

                    Spacer(modifier = Modifier.height(30.dp))
                }
                items(uiState.tasks){ task->
                    taskComponent(task = task, onCardClick = {
                        tsViewModel.sendEvent(
                            event = TaskScreenUIEvents.OnChangeExpandedDialogState(true)
                        )
                        tsViewModel.sendEvent(
                            event = TaskScreenUIEvents.SetTaskToBeExpanded(task)
                        )
                    })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        !uiState.isLoading && uiState.tasks.isEmpty() -> {
            EmptyComponent()
        }
    }

}