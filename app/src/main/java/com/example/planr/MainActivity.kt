package com.example.planr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planr.components.profileHeaderComponent
import com.example.planr.components.startScreenGreeting
import com.example.planr.components.taskComponent
import com.example.planr.data.debugTasks
import com.example.planr.ui.theme.PlanrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanrTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    startingScreen()
                }
            }
        }
    }
}
@Preview
@Composable
fun startingScreen() {
    LazyColumn(
            contentPadding = PaddingValues(
                start=16.dp,
                top = 16.dp,
                bottom = 16.dp,
        )
    ){
        item{
            profileHeaderComponent()
        }
        item{
            Spacer(modifier = Modifier.height(30.dp))

            startScreenGreeting()

            Spacer(modifier = Modifier.height(30.dp))
        }
        items(debugTasks){task->
            taskComponent(task = task)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
