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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import com.example.delegar.Componentes.Micard

@Composable fun InscripcionScreen(modifier: Modifier) {

    var Nombre by rememberSaveable() { mutableStateOf("") }
    val nombreValido = Nombre.length >= 3
    var Correo by rememberSaveable() { mutableStateOf("") }
    val correValido = android.util.Patterns.EMAIL_ADDRESS.matcher(Correo).matches()
    var Edad by rememberSaveable() { mutableStateOf("") }
    val edadValida = (Edad.toIntOrNull() ?: 0) in 12..99
    var Taller by rememberSaveable() { mutableStateOf( "") }
    var Talleres = listOf("Fotografica", "Pintura", "Guitarra")
    var clickio by remember { mutableStateOf(false) }
    val esErrorTaller = Taller.isNotEmpty()
    var expanded by remember { mutableStateOf(false) }
    var textFieldWidth by remember { mutableStateOf(0) }
    val formularioValiod = nombreValido && correValido && edadValida && esErrorTaller


    Column(modifier = modifier
        .fillMaxWidth()
        .padding(15.dp)) {

        Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text("Inscripcion a Talleres")

            Text("Casa de lago * cupo limitado")
        }
        Column(modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp), verticalArrangement = Arrangement.SpaceBetween){

            CampoTexto("Nombre Completo", Nombre, onValueChange = {cambio -> Nombre = cambio}, "Tiene que ver como minimo 3 letras", validator = {text -> text.length >= 3})
            CampoTexto("Correo Electronico", Correo, onValueChange = {cambio -> Correo = cambio}, "Correo invalido, tiene que haber un @ despues un .", validator = {text -> android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()})
            CampoTexto("Edad", Edad, onValueChange = {cambio -> Edad = cambio}, "Edad Invalida", validator = {text -> (text.toIntOrNull() ?: 0) in 12..99 })
            Box(modifier
                .fillMaxWidth()
                .padding(15.dp)){
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned(){ coordinates ->
                            textFieldWidth = coordinates.size.width
                        }
                        .clickable { expanded = true },
                    value = Taller,
                    onValueChange = {
                        cambio ->
                        Taller = cambio
                        clickio = true
                    },
                    readOnly = true,
                    label = { Text("Talleres disponibles") },
                    enabled = false,
                    isError = clickio && !esErrorTaller,
                    supportingText = {
                        if (clickio && !esErrorTaller){
                            Text(
                                text = "Seleccione un taller",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    interactionSource = remember { MutableInteractionSource() }.also {
                        interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect { interaction ->
                                if (interaction is PressInteraction.Release){
                                    expanded = true
                                }
                            }
                        }
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier.width(with(LocalDensity.current) { textFieldWidth.toDp() })
                ) {
                    Talleres.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                Taller = option
                                expanded = false
                            }
                        )
                    }
                }

            }

        }
        Button(onClick = {
            Nombre = ""
            Edad = ""
            Correo = ""
            Taller = ""
        }, modifier.fillMaxWidth(),
            enabled = formularioValiod) {
            Text("Enviar")
        }
    }


}

@Composable
fun CampoTexto(label: String, value: String,
               onValueChange: (String) -> Unit, error: String, validator: (String) -> Boolean = { true }
) {
    var hasInteractered by remember { mutableStateOf(false) }
    val esError = hasInteractered && !validator(value)
    OutlinedTextField(value = value, onValueChange = {cambio -> hasInteractered = true
        onValueChange(cambio)}, label = { Text(text = label)}, modifier = Modifier

        .fillMaxWidth()
        .padding(15.dp),
        isError = esError,
        supportingText = {
            if (esError){
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}

