package com.example.delegar.Componentes

import android.R
import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@Composable
fun Micard(modifier: Modifier, capacidad: String, porcentaje: Float){
    val por100 = porcentaje * 100
    Box(modifier.fillMaxWidth().height(200.dp)
        .border(
            width = 2.dp,
            color = Color(0xFFE8F5E9),
            shape = RoundedCornerShape(12.dp)
    ).background(color = Color(0xFFE8F5E9)), contentAlignment = Alignment.Center)
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = capacidad, fontSize = 50.sp, color = ColorPercent(por100))

            Text(text = "Personas en la sala", color = ColorPercent(por100))
            LinearProgressIndicator(
                progress = { porcentaje },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = ColorPercent(por100),
                trackColor = Color.DarkGray
            )

            Text(text = "${por100.toInt()}% de  ocupacion", color = ColorPercent(por100))

            Text(text = "Aforo Disponible", color = Color.White)
        }
    }
    Spacer(modifier.padding(5.dp))
}

fun ColorPercent(porcentaje: Float): Color {
    return when {
        porcentaje < 70f -> Color(0xFF2E7D32)
        porcentaje in 70f..99f -> Color(0xFFF57F17)
        else -> Color(0xFFC62828)
    }
}


