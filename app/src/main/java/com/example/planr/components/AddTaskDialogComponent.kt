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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.planr.ui.ManageScreenUIState

@Composable
fun AddTaskDialogComponent(
    setTaskTitle: (String) -> Unit,
    setTaskBody: (String) -> Unit,
    setTaskStartTime: (String) -> Unit,
    setTaskEndTime: (String) -> Unit,
    saveTask: () -> Unit,
    closeDialog: () -> Unit,
    uiState: ManageScreenUIState,
){
    Dialog(onDismissRequest = { closeDialog() }) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.fillMaxWidth(),
        ){
            LazyColumn(contentPadding = PaddingValues(12.dp)){
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ){
                        Text(
                            text = "New Task",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                item{
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = uiState.currentTextFieldTitle,
                        onValueChange = { title ->
                            setTaskTitle(title)
                        },
                        label = { Text("Task Title") },
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
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = uiState.currentTextFieldStartTime,
                        onValueChange = { body ->
                            setTaskStartTime(body)
                        },
                        label = { Text("Start Time") },
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = uiState.currentTextFieldEndTime,
                        onValueChange = { body ->
                            setTaskEndTime(body)
                        },
                        label = { Text("End Time") },
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
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                        ) {
                            Text(text = "Save Task")
                        }
                    }
                }
            }
        }
    }
}