package com.example.delegar.View

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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

@Composable
fun AforoVisita(visitas: Int){
    Box(modifier = Modifier.fillMaxWidth().height(100.dp).border(
        width = 2.dp,
        color = Color(0xFFF3EEF6),
        shape = RoundedCornerShape(12.dp)
    ).background(Color(0xFFF3EEF6))){
        Column(Modifier.fillMaxSize().padding(10.dp), horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween) {
            Text("Visitas del Dia", color = Color(0xFF4A4458), fontSize = 18.sp)
            Text("Solo suma visitas del dia", color = Color(0xFF4A4458), fontSize = 14.sp)

        }
        Column(Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
            Text("$visitas", fontSize = 40.sp, color = Color(0xFF4A4458))
        }
    }

}