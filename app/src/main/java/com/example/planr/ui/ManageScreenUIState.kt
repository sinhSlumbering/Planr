package com.example.planr.ui

import com.example.planr.data.model.Task
import com.example.planr.data.model.TaskProgress

data class ManageScreenUIState (
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val errorMessage: String? = null,
    val taskToBeUpdated: Task? = null,
    val isShowAddTaskDialog: Boolean = false,
    val isShowUpdateTaskDialog: Boolean = false,
    val currentTextFieldTitle: String = "",
    val currentTextFieldBody: String = "",
    val currentTextFieldStartTime: String = "",
    val currentTextFieldEndTime: String = "",
    val currentProgress: TaskProgress = TaskProgress.UNSCHEDULED,
)