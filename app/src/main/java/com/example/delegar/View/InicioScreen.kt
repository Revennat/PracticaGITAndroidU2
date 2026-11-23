package com.example.delegar.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.delegar.ViewModel.LecturaViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delegar.Model.Interlineado


@Composable
fun LecturaScreen(modifier: Modifier, viewModel: LecturaViewModel = viewModel()){

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Text("<-", fontSize = 20.sp, color = Color.Black)
            Text("Ajustes de Lectura", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
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
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Tamaño de letra", fontSize = 16.sp, color = Color.Black)
                        Text("${uiState.tamano.toInt()} sp", fontSize = 16.sp, color = Color(0xFF6750A4), fontWeight = FontWeight.Medium)
                    }
                    Slider(
                        value = uiState.tamano,
                        onValueChange = { nuevoFloat -> viewModel.onChangeTamano(nuevoFloat) },
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
                        Text("A", fontSize = 14.sp, color = Color.DarkGray)
                        Text("A", fontSize = 22.sp, color = Color.DarkGray)
                    }
                }

                HorizontalDivider(color = Color(0xFFE2D9EC))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Interlineado", fontSize = 16.sp, color = Color.Black)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = ( uiState.opcion == Interlineado.COMPACTO),
                                onClick = { viewModel.onCompcto() }
                            )
                            Text("Compacto", fontSize = 13.sp, color = Color.Black)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = ( uiState.opcion == Interlineado.NORMAL),
                                onClick = { viewModel.onNormal() }
                            )
                            Text("Normal", fontSize = 13.sp, color = Color.Black)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = ( uiState.opcion == Interlineado.AMPLIO),
                                onClick = { viewModel.onAmplio() }
                            )
                            Text("Amplio", fontSize = 13.sp, color = Color.Black)
                        }
                    }
                }
                HorizontalDivider(color = Color(0xFFE2D9EC))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Modo Oscuro", fontSize = 16.sp, color = Color.Black)
                    Switch(
                        checked = uiState.mdOscuro,
                        onCheckedChange = { viewModel.onCheckedmdOscuro(it) }
                    )
                }

                HorizontalDivider(color = Color(0xFFE2D9EC))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Fuente", fontSize = 16.sp, color = Color.Black)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = (!uiState.serif),
                            onClick = { viewModel.onClickfalse() },
                            label = { Text("Sans") }
                        )
                        FilterChip(
                            selected = (uiState.serif),
                            onClick = { viewModel.onClicktrue() },
                            label = { Text("Serif") }
                        )
                    }
                }
            }
        }
        VistaPrevia("Hablo con Marulete, viene el burro y se la mete" , uiState.tamano, uiState.opcion, uiState.mdOscuro, uiState.serif)
        Button(modifier = modifier.fillMaxWidth(), onClick = { viewModel.restaurar() }) { Text("Restaurar a predeterminado", fontSize = 16.sp)}
    }
}



@Composable
fun VistaPrevia(texto: String, tamano: Float, interlineado: Interlineado, modoOscuro: Boolean, usaSerif: Boolean)
{
    Box(Modifier.fillMaxWidth().background(if(modoOscuro) Color.Black else Color.White).height(180.dp).padding(15.dp), contentAlignment = Alignment.Center){
        Text(texto, fontSize = tamano.toInt().sp, lineHeight = interlineado.factor.em, fontFamily = if (usaSerif) FontFamily.Serif else FontFamily.SansSerif, color = if (modoOscuro) Color.White else Color.Black)
    }

}