package com.example.planr.ui.events

import com.example.planr.data.model.Task
import com.example.planr.data.model.TaskProgress

sealed class ManageScreenUIEvents{
    object  GetTasks : ManageScreenUIEvents()

    data class AddTask(
        val title: String,
        val body: String,
        val startTime: String,
        val endTime: String,
        val progress: TaskProgress
    ): ManageScreenUIEvents()

    object UpdateTask : ManageScreenUIEvents()

    data class DeleteTask(val id: String): ManageScreenUIEvents()
    data class OnChangeTaskTitle(val title: String?): ManageScreenUIEvents()

    data class OnChangeTaskBody(val body: String): ManageScreenUIEvents()

    data class OnChangeStartTime(val startTime: String): ManageScreenUIEvents()
    data class OnChangeEndTime(val endTime: String): ManageScreenUIEvents()

    data class OnChangeAddTaskDialogState(val show: Boolean): ManageScreenUIEvents()

    data class OnChangeUpdateDialogState(val show: Boolean): ManageScreenUIEvents()

    data class SetTaskToBeUpdated(val taskToBeUpdated: Task): ManageScreenUIEvents()

    data class OnChangeDropDownExpanded(val expanded: Boolean): ManageScreenUIEvents()

    data class OnChangeDropDownOption(val progress: String): ManageScreenUIEvents()

}
