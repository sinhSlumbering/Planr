package com.example.planr.fragments

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planr.components.profileHeaderComponent
import com.example.planr.components.startScreenGreeting
import com.example.planr.components.taskComponent
import com.example.planr.data.debugTasks

@Composable
fun taskScreen(
    onClickProfile: () ->Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start=16.dp,
            top = 16.dp,
            bottom = 16.dp,
        )
    ){
        item{
            profileHeaderComponent(
                onClickProfile = { onClickProfile() }
            )
        }
        item{
            Spacer(modifier = Modifier.height(30.dp))

            startScreenGreeting()

            Spacer(modifier = Modifier.height(30.dp))
        }
        items(debugTasks){ task->
            taskComponent(task = task)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}