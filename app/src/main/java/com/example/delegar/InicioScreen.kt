package com.example.delegar

import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import com.example.delegar.Componentes.Micard
import kotlin.collections.listOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(modifier: Modifier = Modifier) {
    val correoCorrecto = "paciente@vida.mx"
    val passCorrecta = "Vida2025"

    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var recordarUsuario by remember { mutableStateOf(false) }
    var intentos by remember { mutableStateOf(0) }
    var mensajeError by remember { mutableStateOf("") }
    var isSesionIniciada by remember { mutableStateOf(false) }
    val isBotonBloqueado = intentos >= 3

    if (!isSesionIniciada) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Logo",
                tint = Color(0xFF6750A4),
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Clínica Vida", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("Consulta tus citas médicas", color = Color.Gray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val imagenIcono = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = imagenIcono, contentDescription = "Mostrar contraseña")
                    }
                }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clickable { recordarUsuario = !recordarUsuario }
            ) {
                Checkbox(
                    checked = recordarUsuario,
                    onCheckedChange = { recordarUsuario = it }
                )
                Text("Recordarme", fontSize = 14.sp, color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.height(16.dp))
            if (mensajeError.isNotEmpty()) {
                Surface(
                    color = if (isBotonBloqueado) Color(0xFFFFCDD2) else Color(0xFFFBE9E7),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = mensajeError,
                        color = Color(0xFFC62828),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            Button(
                onClick = {
                    if (correo == correoCorrecto && password == passCorrecta) {
                        correo = ""
                        password = ""
                        mensajeError = ""
                        intentos = 0
                        isSesionIniciada = true
                    } else {
                        intentos++
                        correo = ""
                        password = ""

                        if (intentos >= 3) {
                            mensajeError = "Acceso bloqueado por seguridad. Intenta más tarde."
                        } else {
                            mensajeError = "ingreso correctame la cuenta (intento $intentos de 3)"
                        }
                    }
                },
                enabled = !isBotonBloqueado,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Entrar", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Enlace de contraseña decorativo
            Text(
                text = "¿Olvidaste tu contraseña?",
                color = Color(0xFF6750A4),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { /* No hace nada */ }
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("¡Bienvenido!", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6750A4))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Has iniciado sesión como:")
            Text(correoCorrecto, fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedButton(
                onClick = { isSesionIniciada = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Cerrar sesión")
            }
        }
    }


}

