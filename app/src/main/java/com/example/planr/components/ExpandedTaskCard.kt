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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.planr.data.model.Task
import com.example.planr.data.model.TaskProgress
import com.example.planr.ui.states.TaskScreenUIState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalStdlibApi::class)
@Composable
fun ExpandedTaskCard(
    task: Task?,
    closeDialog: ()-> Unit,
    onExpandedChange: (Boolean) -> Unit,
    uiState: TaskScreenUIState,
    selectedProgress: (String) -> Unit
){
    Dialog(onDismissRequest = { closeDialog() }) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.fillMaxWidth(),
        ){
            LazyColumn(contentPadding = PaddingValues(12.dp)){
                item{
                    Text(
                        text = task?.title.toString() ?: "",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                        )
                }
                item{
                    Text(
                        text = task?.body ?:"",
                    )
                }
                item{
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold,)){
                                append("Start Time: ")
                            }
                            append(task?.startTime)
                        }
                    )
                }
                item{
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold,)){
                                append("End Time:   ")
                            }
                            append(task?.endTime)
                        }
                    )
                }
                item{
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold,)){
                                append("Created At: ")
                            }
                            append(task?.createdAt)
                        }
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

            }

        }

    }
}

//@Preview
//@Composable
//fun prevExpandedCard(){
//    ExpandedTaskCard(task = debugTasks[1], {}, {})
//}