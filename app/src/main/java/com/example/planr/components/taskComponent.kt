package com.example.planr.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.planr.data.model.Task


@Composable
fun taskComponent (task: Task){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Text(
            text = "${task.startTime}\nAM",
            textAlign = TextAlign.Center,
        )
        Row (verticalAlignment = Alignment.CenterVertically){
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .border(
                        border = BorderStroke(3.dp, MaterialTheme.colorScheme.secondary),
                        shape = CircleShape,
                    )
            )

            Divider(
                modifier = Modifier.width(6.dp),
                color = MaterialTheme.colorScheme.secondary,
                )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Column (
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .weight(0.9f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${task.title}",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            top = 12.dp,
                            start = 12.dp,
                        )
                    )
                    if(task.body != null){
                        Text(
                            text = "${task.body}",
                            modifier = Modifier.padding(
                                start = 12.dp,
                            ),
                            color = Color.Gray
                        )
                    }

                    Text(
                        text = "${task.startTime} - ${task.endTime}",
                        modifier = Modifier.padding(
                            start = 12.dp,
                            bottom = 12.dp,
                        ),
                    )
                }
                Divider(
                    modifier = Modifier
                        .width(6.dp)
                        .weight(0.1f),
                    color = MaterialTheme.colorScheme.secondary,
                )
            }


        }
    }
}