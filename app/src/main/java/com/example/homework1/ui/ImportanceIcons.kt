package com.example.homework1.ui

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.homework1.domain.models.Importance
import com.example.homework1.R

@Composable
fun ImportanceIcon(importance: Importance, modifier: Modifier = Modifier) {
    when (importance) {
        Importance.HIGH -> {
            Icon(
                painterResource(R.drawable.important),
                contentDescription = null,
                tint = Color.Red,
                modifier = modifier
            )
        }
        Importance.LOW -> {
            Icon(
                painterResource(R.drawable.not_important),
                contentDescription = null,
                tint = Color.Gray,
                modifier = modifier
            )
        }
        else -> {}
    }
}