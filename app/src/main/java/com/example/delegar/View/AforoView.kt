package com.example.delegar.View

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.delegar.ViewModel.AforoViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState

@Composable
fun AforoScreen(modifier: Modifier, viewModel: AforoViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()


    Box(modifier.fillMaxSize().padding(15.dp)){
        Column(modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth()) {
                Text("Biblioteca Central", fontSize = 25.sp)
            }
            Spacer(modifier.height(50.dp))
            Text("Sala de estudio * Capacidad ${uiState.aforoMax}", fontSize = 10.sp, color = Color.DarkGray)
            Micard(modifier = modifier, capacidad = "${uiState.actuales}/${uiState.aforoMax}", uiState.porcentaje)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.registrarSalida() },
                    modifier = Modifier.weight(1f),
                    enabled = uiState.actuales > 0,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF6750A4)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF6750A4))
                ) {
                    Text("Sale -")
                }

                Button(
                    onClick = { viewModel.registrarEnterada() },
                    modifier = Modifier.weight(1f),
                    enabled = uiState.actuales < 50,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6750A4),
                        contentColor = Color.White
                    )
                ) {
                    Text("Entra +")
                }
            }
            Spacer(modifier.height(10.dp))
            AforoVisita(uiState.visitasDelDia)
            Row(modifier.fillMaxWidth().height(300.dp), verticalAlignment = Alignment.Bottom) {
                OutlinedButton(onClick = { viewModel.cerrarDia() },
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

