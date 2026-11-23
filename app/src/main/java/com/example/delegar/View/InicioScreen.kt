package com.example.delegar.View

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.delegar.ViewModel.InscripcionViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable fun InscripcionScreen(modifier: Modifier, viewModel: InscripcionViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

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

            CampoTexto(
                "Nombre Completo",
                uiState.nombre,
                onValueChange = {viewModel.onChangeNombre(it)},
                "Tiene que ver como minimo 3 letras",
                validator = {viewModel.validarNombre(it)})
            CampoTexto(
                "Correo Electronico",
                uiState.correo,
                onValueChange = {viewModel.onChangeCorreo(it)},
                "Correo invalido, tiene que haber un @ despues un .",
                validator = {viewModel.validarCorreo(it)})
            CampoTexto(
                "Edad",
                uiState.edad,
                onValueChange = {viewModel.onChangeEdad(it)},
                "Edad Invalida",
                validator = {viewModel.validarEdad(it)})
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { viewModel.onGlobalWidht(it) }
                        .clickable { viewModel.onExpanded() }, // o la función que abra el menú
                    value = uiState.tallerSeleccionado,
                    onValueChange = { viewModel.onChangeTaller(it) },
                    readOnly = true,
                    enabled = false, // <-- Quita esta línea (o ponla en true)
                    label = { Text("Talleres disponibles") },
                    isError = uiState.click && !uiState.esErrorTaller,
                    supportingText = {
                        if (uiState.click && !uiState.esErrorTaller) {
                            Text(
                                text = "Seleccione un taller",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                DropdownMenu(
                    expanded = uiState.expanded,
                    onDismissRequest = { viewModel.onDimiss() },
                    modifier = Modifier.width(
                        with(LocalDensity.current) { uiState.textWidth.toDp() }
                    )
                ) {
                    uiState.talleresDisponibles.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = { viewModel.onChangeDimiss(option) }
                        )
                    }
                }
            }

        }
        Button(onClick = { viewModel.onEnviar() }, modifier.fillMaxWidth(),
            enabled = uiState.formularioValido) {
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

