package com.example.planr.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.planr.data.model.Task
import com.example.planr.ui.ManageScreenUIState

@Composable
fun UpdateTaskDialogComponent(
    setTaskTitle: (String) -> Unit,
    setTaskBody: (String) -> Unit,
    setTaskStartTime: (String) -> Unit,
    setTaskEndTime: (String) -> Unit,
    saveTask: () -> Unit,
    closeDialog: () -> Unit,
    task: Task?,
    uiState: ManageScreenUIState,
) {
    Dialog(onDismissRequest = { closeDialog() }) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
        ) {
            LazyColumn(contentPadding = PaddingValues(12.dp)) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "Update Task",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = uiState.currentTextFieldTitle,
                        onValueChange = { title ->
                            setTaskTitle(title)
                        },
                        label = { Text("Task Title") },
                        placeholder = { Text(task?.title.toString() ?: "") },

                        )
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = uiState.currentTextFieldBody,
                        onValueChange = { body ->
                            setTaskBody(body)
                        },
                        label = { Text("Task Body") },
                        placeholder = { Text(task?.body ?: "") },

                        )
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = uiState.currentTextFieldBody,
                        onValueChange = { startTime ->
                            setTaskStartTime(startTime)
                        },
                        label = { Text("Start Time") },
                        placeholder = { Text(task?.body ?: "") },

                        )
                }
                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = uiState.currentTextFieldBody,
                        onValueChange = { endTime ->
                            setTaskEndTime(endTime)
                        },
                        label = { Text("End Time") },
                        placeholder = { Text(task?.body ?: "") },

                        )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = {
                                saveTask()
                                setTaskTitle("")
                                setTaskBody("")
                                closeDialog()
                            },
                            modifier = Modifier.padding(horizontal = 12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White,
                            ),
                        ) {
                            Text(text = "Update Task")
                        }
                    }
                }
            }
        }
    }
}