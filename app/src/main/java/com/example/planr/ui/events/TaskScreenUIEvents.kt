package com.example.planr.ui.events

import com.example.planr.data.model.Task

sealed class TaskScreenUIEvents {
    object GetTasks: TaskScreenUIEvents()

    object UpdateTask: TaskScreenUIEvents()

    data class SetTaskToBeUpdated(val taskToBeUpdated: Task): TaskScreenUIEvents()

    data class SetTaskToBeExpanded(val taskToBeExpanded: Task):TaskScreenUIEvents()

    data class OnChangeExpandedDialogState(val show: Boolean): TaskScreenUIEvents()

    data class OnChangeDropDownExpanded(val expanded: Boolean): TaskScreenUIEvents()

    data class OnChangeDropDownOption(val progress: String): TaskScreenUIEvents()
}