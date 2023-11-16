package com.example.planr.ui.states

import com.example.planr.data.model.Task

data class ManageScreenUIState (
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val errorMessage: String? = null,
    val taskToBeUpdated: Task? = null,
    val isShowAddTaskDialog: Boolean = false,
    val isShowUpdateTaskDialog: Boolean = false,
    val isProgressMenuExpanded: Boolean = false,
    val selectedProgress: String? = "UNSCHEDULED",
    val currentTextFieldTitle: String? = null,
    val currentTextFieldBody: String? = null,
    val currentTextFieldStartTime: String? = null,
    val currentTextFieldEndTime: String? = null,
)