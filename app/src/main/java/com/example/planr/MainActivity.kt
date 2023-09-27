package com.example.planr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.planr.navgraphs.RootNavGraph
import com.example.planr.ui.theme.PlanrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanrTheme {
                // A surface container using the 'background' color from the theme
                RootNavGraph(navController = rememberNavController())
            }
        }
    }
}

