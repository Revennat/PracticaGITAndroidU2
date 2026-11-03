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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.delegar.Componentes.Micard

enum class Interlineado(val factor: Float) {
    COMPACTO(1.0f), NORMAL(1.3f), AMPLIO(1.6f)
}

@Composable
fun LecturaScreen(modifier: Modifier){
    var tamano by remember { mutableStateOf(22f) }
    var opcion by remember { mutableStateOf(Interlineado.NORMAL) }
    var mdOscuro by remember { mutableStateOf(true) }
    var Serif by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Cabecera
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Text("<-", fontSize = 20.sp, color = Color.Black)
            Text("Ajustes de Lectura", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }

        // Tarjeta contenedora de ajustes
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = Color(0xFFF3EEF6),
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // --- TAMAÑO DE LETRA ---
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Tamaño de letra", fontSize = 16.sp, color = Color.Black)
                        Text("${tamano.toInt()} sp", fontSize = 16.sp, color = Color(0xFF6750A4), fontWeight = FontWeight.Medium)
                    }

                    Slider(
                        value = tamano,
                        onValueChange = { nuevoTamano -> tamano = nuevoTamano },
                        valueRange = 12f..36f,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF6750A4),
                            activeTrackColor = Color(0xFF6750A4),
                            inactiveTrackColor = Color(0xFFE8DEF8)
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Subidos de tono a un gris más oscuro (casi negro) para mayor legibilidad
                        Text("A", fontSize = 14.sp, color = Color.DarkGray)
                        Text("A", fontSize = 22.sp, color = Color.DarkGray)
                    }
                }

                HorizontalDivider(color = Color(0xFFE2D9EC))

                // --- INTERLINEADO ---
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Interlineado", fontSize = 16.sp, color = Color.Black)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = (opcion == Interlineado.COMPACTO),
                                onClick = { opcion = Interlineado.COMPACTO }
                            )
                            Text("Compacto", fontSize = 13.sp, color = Color.Black)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = (opcion == Interlineado.NORMAL),
                                onClick = { opcion = Interlineado.NORMAL }
                            )
                            Text("Normal", fontSize = 13.sp, color = Color.Black)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = (opcion == Interlineado.AMPLIO),
                                onClick = { opcion = Interlineado.AMPLIO }
                            )
                            Text("Amplio", fontSize = 13.sp, color = Color.Black)
                        }
                    }
                }

                HorizontalDivider(color = Color(0xFFE2D9EC))

                // --- MODO OSCURO ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Modo Oscuro", fontSize = 16.sp, color = Color.Black)
                    Switch(
                        checked = mdOscuro,
                        onCheckedChange = { mdOscuro = it }
                    )
                }

                HorizontalDivider(color = Color(0xFFE2D9EC))

                // --- FUENTE ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Fuente", fontSize = 16.sp, color = Color.Black)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = (!Serif),
                            onClick = { Serif = false },
                            label = { Text("Sans") }
                        )
                        FilterChip(
                            selected = (Serif),
                            onClick = { Serif = true },
                            label = { Text("Serif") }
                        )
                    }
                }
            }
        }




        VistaPrevia("Hablo con Marulete, viene el burro y se la mete" , tamano, opcion, mdOscuro, Serif)


        Button(modifier = modifier.fillMaxWidth(), onClick = {
            Serif = false
            mdOscuro = true
            opcion = Interlineado.NORMAL
            tamano = 22f
        }) { Text("Restaurar a predeterminado", fontSize = 16.sp)}


    }


}



@Composable
fun VistaPrevia(texto: String, tamano: Float, interlineado: Interlineado, modoOscuro: Boolean, usaSerif: Boolean)
{
    Box(Modifier.fillMaxWidth().background(if(modoOscuro) Color.Black else Color.White).height(180.dp).padding(15.dp), contentAlignment = Alignment.Center){
        Text(texto, fontSize = tamano.toInt().sp, lineHeight = interlineado.factor.em, fontFamily = if (usaSerif) FontFamily.Serif else FontFamily.SansSerif, color = if (modoOscuro) Color.White else Color.Black)
    }

}