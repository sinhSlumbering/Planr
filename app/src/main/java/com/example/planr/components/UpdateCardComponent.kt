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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.planr.data.model.Task
import com.example.planr.data.model.TaskProgress
import com.example.planr.ui.states.ManageScreenUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTaskDialogComponent(
    setTaskTitle: (String?) -> Unit,
    setTaskBody: (String?) -> Unit,
    setTaskStartTime: (String?) -> Unit,
    setTaskEndTime: (String?) -> Unit,
    saveTask: () -> Unit,
    closeDialog: () -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    selectedProgress: (String?) -> Unit,
    task: Task?,
    uiState: ManageScreenUIState,
) {
    LaunchedEffect(key1 = "setValuesUpdateCard"){
        setTaskTitle(task?.title)
        setTaskBody(task?.body)
        setTaskStartTime(task?.startTime)
        setTaskEndTime(task?.endTime)
        selectedProgress(task?.progress.toString())
    }
    Dialog(onDismissRequest = { closeDialog() }) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.secondaryContainer,
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
                        value = (uiState.currentTextFieldTitle?:task?.title)?:"",
                        onValueChange = { title ->
                            setTaskTitle(title)
                        },
                        label = { Text("Task Title") },
//                        placeholder = { Text(task?.title.toString() ?: "") },

                        )
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = (uiState.currentTextFieldBody?:task?.body)?:"",
                        onValueChange = { body ->
                            setTaskBody(body)
                        },
                        label = { Text("Task Body") },
//                        placeholder = { Text(task?.body ?: "") },

                        )
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = (uiState.currentTextFieldStartTime?:task?.startTime)?:"",
                        onValueChange = { startTime ->
                            setTaskStartTime(startTime)
                        },
                        label = { Text("Start Time") },
                        placeholder = { Text(task?.startTime ?: "") },

                        )
                }
                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = (uiState.currentTextFieldEndTime?:task?.endTime)?:"",
                        onValueChange = { endTime ->
                            setTaskEndTime(endTime)
                        },
                        label = { Text("End Time") },
                        placeholder = { Text(task?.endTime ?: "") },

                        )
                }
                item{
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.Center
                    ){
                        ExposedDropdownMenuBox(
                            expanded = uiState.isProgressMenuExpanded,
                            onExpandedChange = {
                                onExpandedChange(!uiState.isProgressMenuExpanded)
                            }
                        ) {
                            TextField(
                                readOnly = true,
                                value = uiState.selectedProgress ?: task?.progress.toString(),
                                onValueChange = {},
                                label ={ Text(text = "Task Progress")},
                                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                modifier = Modifier
                                    .menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = uiState.isProgressMenuExpanded,
                                onDismissRequest = {
                                    onExpandedChange(false)
                                }
                            ) {
                                TaskProgress.values().forEach { option->
                                    DropdownMenuItem(
                                        text = { Text(option.toString())},
                                        onClick = {
                                            selectedProgress(option.toString())
                                            onExpandedChange(false)
                                        }
                                    )
                                }
                            }
                        }
                    }
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