package com.example.planr.ui.states

import com.example.planr.data.model.Task

data class TaskScreenUIState (
    val isLoading: Boolean = false,
    val tasks : List<Task> = emptyList(),
    val taskToBeUpdated: Task? = null,
    val taskToBeExpanded: Task? = null,
    val errorMessage: String? = null,
    val isShowExpandedCard: Boolean = false,
    val isProgressMenuExpanded: Boolean = false,
    val selectedProgress: String? = "UNSCHEDULED"
)