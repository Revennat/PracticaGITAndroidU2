package com.example.delegar

import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment

import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


data class Pregunta(
    val enunciado: String,
    val opciones: List<String>,
    val indiceCorrecta: Int,
    val retroalimentacion: String
)

enum class PantallaQuiz { INICIO, JUEGO, RESULTADOS }

val listaPreguntas = listOf(
    Pregunta(
        "¿Qué tipo de dato conviene usar para guardar el valor 3.14?",
        listOf("Int", "Double", "String", "Boolean"),
        1,
        "Incorrecto. La respuesta correcta es Double."
    ),
    Pregunta(
        "¿Qué palabra reservada se usa para declarar una variable inmutable en Kotlin?",
        listOf("var", "let", "val", "const"),
        2,
        "Incorrecto. La respuesta correcta es 'val'."
    ),
    Pregunta(
        "¿Cuál es el símbolo del operador lógico AND?",
        listOf("||", "&&", "==", "!="),
        1,
        "Incorrecto. La respuesta correcta es &&."
    ),
    Pregunta(
        "¿Cómo se inicializa un arreglo vacío de enteros en Kotlin?",
        listOf("[]", "Array()", "arrayOf()", "intArrayOf()"),
        3,
        "Incorrecto. La respuesta correcta es intArrayOf()."
    ),
    Pregunta(
        "¿Qué significa POO?",
        listOf("Programación Orientada a Objetos", "Procesamiento de Operaciones", "Paradigmas Orientados", "Ninguna"),
        0,
        "Incorrecto. Significa Programación Orientada a Objetos."
    )
)

@Composable
fun InicioScreen(modifier: Modifier = Modifier) {

    var pantallaActual by remember { mutableStateOf(PantallaQuiz.INICIO) }
    var nombreJugador by remember { mutableStateOf("") }

    var indicePregunta by remember { mutableIntStateOf(0) }
    var puntaje by remember { mutableIntStateOf(0) }

    when (pantallaActual) {
        PantallaQuiz.INICIO -> {
            PantallaInicio(
                nombre = nombreJugador,
                onNombreChange = { nombreJugador = it },
                onComenzar = { pantallaActual = PantallaQuiz.JUEGO }
            )
        }
        PantallaQuiz.JUEGO -> {
            val preguntaActual = listaPreguntas[indicePregunta]

            PantallaPregunta(
                pregunta = preguntaActual,
                numeroActual = indicePregunta + 1,
                totalPreguntas = listaPreguntas.size,
                puntaje = puntaje,
                onSiguiente = { acerto ->
                    if (acerto) puntaje++

                    if (indicePregunta < listaPreguntas.size - 1) {
                        indicePregunta++
                    } else {
                        pantallaActual = PantallaQuiz.RESULTADOS
                    }
                }
            )
        }
        PantallaQuiz.RESULTADOS -> {
            PantallaResultados(
                nombre = nombreJugador,
                puntaje = puntaje,
                total = listaPreguntas.size,
                onReintentar = {
                    indicePregunta = 0
                    puntaje = 0
                    pantallaActual = PantallaQuiz.JUEGO
                },
                onCambiarJugador = {
                    nombreJugador = ""
                    indicePregunta = 0
                    puntaje = 0
                    pantallaActual = PantallaQuiz.INICIO
                }
            )
        }
    }
}

@Composable
fun PantallaInicio(nombre: String, onNombreChange: (String) -> Unit, onComenzar: () -> Unit) {
    val isNombreValido = nombre.trim().length >= 2

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Quiz de programación", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Fundamentos - 5 preguntas", color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Tu nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onComenzar,
            enabled = isNombreValido,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Comenzar")
        }
    }
}


@Composable
fun PantallaPregunta(
    pregunta: Pregunta,
    numeroActual: Int,
    totalPreguntas: Int,
    puntaje: Int,
    onSiguiente: (Boolean) -> Unit
) {
    var opcionSeleccionada by remember(pregunta) { mutableIntStateOf(-1) }
    var isConfirmado by remember(pregunta) { mutableStateOf(false) }
    val isUltimaPregunta = numeroActual == totalPreguntas

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Quiz de programación", fontSize = 20.sp, fontWeight = FontWeight.Medium)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Pregunta $numeroActual de $totalPreguntas", color = Color.Gray, fontSize = 12.sp)
            Text("Puntaje: $puntaje", color = Color(0xFF6750A4), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        LinearProgressIndicator(
            progress = numeroActual.toFloat() / totalPreguntas,
            color = Color(0xFF6750A4),
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(pregunta.enunciado, fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        pregunta.opciones.forEachIndexed { index, textoOpcion ->
            val isSelected = opcionSeleccionada == index
            val isCorrecta = index == pregunta.indiceCorrecta

            // Determinamos el color basado en si ya confirmó
            val colorFondo = when {
                !isConfirmado -> if (isSelected) Color(0xFFF3E5F5) else Color.Transparent
                isConfirmado && isCorrecta -> Color(0xFFE8F5E9) // Verde (Correcta)
                isConfirmado && isSelected && !isCorrecta -> Color(0xFFFFEBEE) // Rojo (Incorrecta seleccionada)
                else -> Color.Transparent
            }

            val colorBorde = when {
                !isConfirmado -> if (isSelected) Color(0xFF6750A4) else Color.LightGray
                isConfirmado && isCorrecta -> Color(0xFF4CAF50)
                isConfirmado && isSelected && !isCorrecta -> Color(0xFFE53935)
                else -> Color.LightGray
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = colorFondo),
                border = BorderStroke(1.dp, colorBorde),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !isConfirmado) { opcionSeleccionada = index } // Bloquea tras confirmar
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = isSelected,
                            onClick = null // Se maneja en el Card
                        )
                        Text(textoOpcion, modifier = Modifier.padding(start = 8.dp))
                    }

                    // Iconos de feedback (solo visibles al confirmar)
                    if (isConfirmado) {
                        if (isCorrecta) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF4CAF50))
                        } else if (isSelected) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = Color(0xFFE53935))
                        }
                    }
                }
            }
        }

        // Mensaje de retroalimentación
        if (isConfirmado && opcionSeleccionada != pregunta.indiceCorrecta) {
            Surface(
                color = Color(0xFFFFEBEE),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text(
                    text = pregunta.retroalimentacion,
                    color = Color(0xFFC62828),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botones de acción
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = { isConfirmado = true },
                enabled = opcionSeleccionada != -1 && !isConfirmado,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8DEF8), contentColor = Color(0xFF6750A4)),
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                Text("Confirmar")
            }

            Button(
                onClick = { onSiguiente(opcionSeleccionada == pregunta.indiceCorrecta) },
                enabled = isConfirmado,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)),
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                Text(if (isUltimaPregunta) "Ver resultados" else "Siguiente")
            }
        }
    }
}

@Composable
fun PantallaResultados(
    nombre: String,
    puntaje: Int,
    total: Int,
    onReintentar: () -> Unit,
    onCambiarJugador: () -> Unit
) {
    val mensaje = when (puntaje) {
        total -> "¡Perfecto!"
        in 3..4 -> "Buen trabajo"
        else -> "Sigue practicando"
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¡$mensaje, $nombre!", fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "$puntaje de $total",
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF6750A4)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onReintentar,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Reintentar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onCambiarJugador,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Cambiar jugador", color = Color(0xFF6750A4))
        }
    }
}
