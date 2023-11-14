package com.example.planr.data.repositories

import com.example.planr.common.Result
import com.example.planr.data.model.Task
import com.example.planr.data.model.TaskProgress
interface TaskRepository {
    suspend fun addTask(title: String, body: String, startTime: String, endTime: String): Result<Unit>

    suspend fun getAllTasks(): Result<List<Task>>

    suspend fun deleteTask(id: String): Result<Unit>

    suspend fun updateTask(id: String, title: String,  body: String, startTime: String, endTime: String, progress: TaskProgress): Result<Unit>
}