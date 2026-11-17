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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import com.example.delegar.Componentes.Micard
import kotlin.collections.listOf


enum class Prioridad(int: Int){
    BAJA(1), MEDIA(2), ALTA(3)
}
enum class Filtro{
     TODOS,PENDIENTES, HECHAS
}

data class Tarea(val id: Int, val titulo: String, val prioridad: Prioridad, val hecha: Boolean = false)

@Composable
fun TareasScreen(modifier: Modifier) {
    var contador = 0

    val tareas = remember { mutableStateListOf<Tarea>() }
    var tarea by remember { mutableStateOf("") }
    var prioridadNueva by remember { mutableStateOf(Prioridad.ALTA) }
    var filtroActual by remember { mutableStateOf(Filtro.TODOS) }
    var mostrarDialogo by remember { mutableStateOf(false) }
    val tareasVisibles by remember {
        derivedStateOf {
            when (filtroActual) {
                Filtro.TODOS -> tareas
                Filtro.PENDIENTES -> tareas.filter { !it.hecha }
                Filtro.HECHAS -> tareas.filter { it.hecha }
            }
        }
    }

    val pendientes by remember {
        derivedStateOf {
            tareas.count() { !it.hecha }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 46.dp, bottom = 46.dp, start = 16.dp, end = 16.dp), // Margen general para que no pegue a los bordes del teléfono
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Título
        Text(
            text = "Mis pendientes",
            style = MaterialTheme.typography.titleLarge
        )

        // Fila de Input y Botón de Agregar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = tarea,
                onValueChange = { cambio -> tarea = cambio },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Nueva tarea...") }
            )
            Button(
                onClick = {
                    if (tarea.isNotEmpty()) {
                        tareas.add(Tarea(contador++, tarea.trim(), prioridadNueva))
                        tarea = ""
                    }
                },
                enabled = tarea.isNotEmpty()
            ) {
                Text("+")
            }
        }

        // Fila de Prioridades
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Prioridad: ")
            FilterChip(
                selected = prioridadNueva == Prioridad.ALTA,
                onClick = { prioridadNueva = Prioridad.ALTA },
                label = { Text(if (prioridadNueva != Prioridad.ALTA) "Alta" else "✓ Alta") }
            )
            FilterChip(
                selected = prioridadNueva == Prioridad.MEDIA,
                onClick = { prioridadNueva = Prioridad.MEDIA },
                label = { Text(if (prioridadNueva != Prioridad.MEDIA) "Media" else "✓ Media") }
            )
            FilterChip(
                selected = prioridadNueva == Prioridad.BAJA,
                onClick = { prioridadNueva = Prioridad.BAJA },
                label = { Text(if (prioridadNueva != Prioridad.BAJA) "Baja" else "✓ Baja") }
            )
        }

        // Fila de Filtros (Todos, Pendientes, Hechas)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = filtroActual != Filtro.PENDIENTES && filtroActual != Filtro.HECHAS,
                onClick = { filtroActual = Filtro.TODOS },
                label = { Text(if (filtroActual != Filtro.TODOS) "Todos" else "✓ Todos") }
            )
            FilterChip(
                selected = filtroActual == Filtro.PENDIENTES,
                onClick = { filtroActual = Filtro.PENDIENTES },
                label = { Text(if (filtroActual != Filtro.PENDIENTES) "Pendientes" else "✓ Pendientes") }
            )
            FilterChip(
                selected = filtroActual == Filtro.HECHAS,
                onClick = { filtroActual = Filtro.HECHAS },
                label = { Text(if (filtroActual != Filtro.HECHAS) "Hechas" else "✓ Hechas") }
            )
        }

        // Lista de tareas (Con .weight(1f) para que respete el espacio y haga scroll interno)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // <- Clave para que no empuje el footer fuera de la pantalla
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(tareasVisibles) { tareaItem ->
                TareaItem(
                    tarea = tareaItem,
                    onCheckedChange = { cambio ->
                        val index = tareas.indexOf(tareaItem)
                        if (index != -1) {
                            tareas[index] = tareaItem.copy(hecha = cambio)
                        }
                    },
                    onDelete = {
                        tareas.remove(tareaItem) // <- Seguro y evita IndexOutOfBoundsException
                    }
                )
            }
        }

        // Footer inferior fijo
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("Pendientes ${pendientes} de ${tareas.size}")
            Button(
                onClick = { mostrarDialogo = true },
                enabled = tareas.any { it.hecha }
            ) {
                Text("Borrar Completadas")
            }

            if (mostrarDialogo) {
                val cantidadHechas = tareas.count { it.hecha }

                ConfirmarBorradoDialog(
                    cantidad = cantidadHechas,
                    onConfirm = {
                        tareas.removeAll { it.hecha }
                        mostrarDialogo = false
                    },
                    onDismiss = {
                        mostrarDialogo = false
                    }
                )
            }
        }
    }
}
@Composable
fun TareaItem(tarea: Tarea, onCheckedChange: (Boolean) -> Unit, onDelete: () -> Unit){

    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Checkbox(
                    checked = tarea.hecha,
                    onCheckedChange = onCheckedChange
                )
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = tarea.titulo, textDecoration = if (tarea.hecha) TextDecoration.LineThrough else null)
                    Text(
                        text = tarea.prioridad.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = if (tarea.prioridad == Prioridad.ALTA) Color.Red else if (tarea.prioridad == Prioridad.MEDIA) Color.Yellow else Color.Green
                    )
                }
                Button(onClick = onDelete) {
                    Text("x")
                }
            }

            HorizontalDivider()
        }
    }
}

@Composable
fun ConfirmarBorradoDialog(cantidad: Int, onConfirm: () -> Unit, onDismiss: () -> Unit){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Borrar tareas completadas")
        },
        text = {
            Text(text = "¿Estás seguro de que deseas eliminar las $cantidad tareas completadas?")
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}

