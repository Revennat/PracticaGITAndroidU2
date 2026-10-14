package com.example.delegar.Componentes

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@Composable
fun Micard(modifier: Modifier, nombre: String){

    Box(modifier.fillMaxWidth().height(70.dp)
        .border(
            width = 2.dp,
            color = Color.Blue,
            shape = RoundedCornerShape(12.dp)
    ).background(color = StringAColor(nombre)), contentAlignment = Alignment.Center)
    {
        Text(nombre, fontSize = 50.sp, color = Color.White)

    }
    Spacer(modifier.padding(5.dp))
}

fun StringAColor(name: String): Color {
    return when (name.lowercase()) {
        "rojo", "red" -> Color.Red
        "azul", "blue" -> Color.Blue
        "verde", "green" -> Color.Green
        "amarillo", "yellow" -> Color.Yellow
        "magenta" -> Color.Magenta
        "cian", "cyan" -> Color.Cyan
        else -> Color.Gray
    }
}


