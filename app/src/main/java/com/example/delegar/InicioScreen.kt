package com.example.delegar

import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
    var Correo by rememberSaveable() { mutableStateOf("") }
    var Edad by rememberSaveable() { mutableStateOf("") }
    var Taller by rememberSaveable() { mutableStateOf( "") }
    var Talleres = listOf("Fotografica", "Pintura", "Guitarra")
    var expanded by remember { mutableStateOf(false) }


    Column(modifier = modifier.fillMaxWidth().padding(15.dp)) {

        Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text("Inscripcion a Talleres")

            Text("Casa de lago * cupo limitado")
        }
        Column(modifier.fillMaxWidth().wrapContentHeight().padding(5.dp), verticalArrangement = Arrangement.SpaceBetween){

            CampoTexto("Nombre Completo", Nombre, onValueChange = {cambio -> Nombre = cambio}, "cambio")
            CampoTexto("Correo Electronico", Correo, onValueChange = {cambio -> Correo = cambio}, "cambio")
            CampoTexto("Edad", Edad, onValueChange = {cambio -> Edad = cambio}, "cambio")
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 15.dp)
                    .clickable { expanded = true }, // Para abrir al hacer clic (si no usas interactionSource)
                value = Taller, // La variable que almacena la opción elegida
                onValueChange = {

                },
                readOnly = true, // Evita que aparezca el teclado
                label = { Text("Selecciona un taller") }, // El título del campo
                enabled = false
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier.fillMaxWidth()
            ) {
                Talleres.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            Taller = option  // Actualiza la variable con la opción seleccionada
                            expanded = false // Cierra el menú
                        }
                    )
                }
            }

        }
        Button(onClick = {}, modifier.fillMaxWidth()) {

        }
    }


}

@Composable
fun CampoTexto(label: String, value: String,
               onValueChange: (String) -> Unit, error: String?) {
    OutlinedTextField(value = value, onValueChange = onValueChange, label = { Text(text = label)}, modifier = Modifier.fillMaxWidth().padding(15.dp))
}

@Composable
fun ConfirmacionScreen(nombre: String, correo: String,
                       taller: String, onNueva: () -> Unit){

}