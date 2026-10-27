package com.example.delegar

import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.delegar.Componentes.Micard


@Composable
fun AforoScreen(modifier: Modifier) {
    var actuales by remember { mutableStateOf(0) }
    var visitasDelDia by remember { mutableStateOf(0) }
    val AforeMax = 50
    val porcentaje = actuales / AforeMax.toFloat()

    Box(modifier.fillMaxSize().padding(15.dp)){
        Column(modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth()) {
                Text("Biblioteca Central", fontSize = 25.sp)
            }
            Spacer(modifier.height(50.dp))
            Text("Sala de estudio * Capacidad $AforeMax", fontSize = 10.sp, color = Color.DarkGray)
            Micard(modifier = modifier, capacidad = "$actuales/$AforeMax", porcentaje)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { if (actuales > 0){
                        actuales--
                    } },
                    modifier = Modifier.weight(1f),
                    enabled = actuales > 0,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF6750A4)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF6750A4))
                ) {
                    Text("Sale -")
                }

                Button(
                    onClick = {
                        if (actuales < AforeMax) {
                            actuales++
                            visitasDelDia++
                        }},
                    modifier = Modifier.weight(1f),
                    enabled = actuales < 50,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6750A4),
                        contentColor = Color.White
                    )
                ) {
                    Text("Entra +")
                }
            }
            Spacer(modifier.height(10.dp))
            AforoVisita(visitasDelDia)
            Row(modifier.fillMaxWidth().height(300.dp), verticalAlignment = Alignment.Bottom) {
                OutlinedButton(onClick = {
                    actuales = 0
                    visitasDelDia = 0
                },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Red
                    ),
                    border = BorderStroke(1.dp, Color.Red)
                )
                {
                    Text("Cerrar dia")
                }
            }
        }

    }

}


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
