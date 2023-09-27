package com.example.planr.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun startScreenGreeting (){
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Hi User",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
        )
        Text(
            text = "Today's Agenda",
            fontSize = 18.sp,
        )
    }
}