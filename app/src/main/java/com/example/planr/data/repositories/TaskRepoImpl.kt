package com.example.planr.data.repositories

import android.util.Log
import com.example.planr.common.Result
import com.example.planr.common.convertDateFormat
import com.example.planr.common.getCurrentTimeAsString
import com.example.planr.data.model.Task
import com.example.planr.data.model.TaskProgress
import com.example.planr.depinject.IoDispatcher
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class TaskRepoImpl @Inject constructor(
    private val todoAppDB: FirebaseFirestore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : TaskRepository {

    override suspend fun addTask(title: String, body: String, startTime: String, endTime: String): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val task = hashMapOf(
                    "title" to title,
                    "body" to body,
                    "createdAt" to getCurrentTimeAsString(),
                    "startTime" to startTime,
                    "endTime" to endTime,
                    "progress" to TaskProgress.UNSCHEDULED.name // Default progress
                )

                val addTaskTimeout = withTimeoutOrNull(10000L) {
                    todoAppDB.collection("tasks")
                        .add(task)
                }

                if (addTaskTimeout == null) {
                    Log.d("ERROR: ", "check internet connection")
                    Result.Failure(IllegalStateException("check internet connection"))
                }

                Result.Success(Unit)
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")
            Result.Failure(exception = exception)
        }
    }

    override suspend fun getAllTasks(): Result<List<Task>> {
        return try {
            withContext(ioDispatcher) {
                val fetchingTaskTimeout = withTimeoutOrNull(10000L) {
                    todoAppDB.collection("tasks")
                        .get()
                        .await()
                        .documents.map { document ->
                            Task(
                                id = document.id,
                                title = document.getString("title") ?: "",
                                body = document.getString("body") ?: "",
                                createdAt = convertDateFormat(dateString = document.getString("createdAt") ?: ""),
                                startTime = document.getString("startTime") ?: "",
                                endTime = document.getString("endTime") ?: "",
                                progress = TaskProgress.valueOf(document.getString("progress") ?: TaskProgress.UNSCHEDULED.name)
                            )
                        }
                }

                if (fetchingTaskTimeout == null) {
                    Result.Failure(IllegalStateException("check internet connection"))
                }

                Result.Success(fetchingTaskTimeout?.toList() ?: emptyList())
            }
        } catch (exception: Exception) {
            Result.Failure(exception = exception)
        }
    }

    override suspend fun deleteTask(id: String): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val addTaskTimeout = withTimeoutOrNull(10000L) {
                    todoAppDB.collection("tasks")
                        .document(id)
                        .delete()
                }

                if (addTaskTimeout == null) {
                    Log.d("ERROR: ", "check internet connection")
                    Result.Failure(IllegalStateException("check internet connection"))
                }

                Result.Success(Unit)
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")
            Result.Failure(exception = exception)
        }
    }

    override suspend fun updateTask(
        id: String,
        title: String,
        body: String,
        startTime: String,
        endTime: String,
        progress: TaskProgress
    ): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val taskUpdate: Map<String, Any> = hashMapOf(
                    "title" to title,
                    "body" to body,
                    "startTime" to startTime,
                    "endTime" to endTime,
                    "progress" to progress.name
                )

                val addTaskTimeout = withTimeoutOrNull(10000L) {
                    todoAppDB.collection("tasks")
                        .document(id)
                        .update(taskUpdate)
                }

                if (addTaskTimeout == null) {
                    Log.d("ERROR: ", "check internet connection")
                    Result.Failure(IllegalStateException("check internet connection"))
                }

                Result.Success(Unit)
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")
            Result.Failure(exception = exception)
        }
    }
}
