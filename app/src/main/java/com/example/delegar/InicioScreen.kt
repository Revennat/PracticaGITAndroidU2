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
import androidx.compose.ui.Alignment
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.composed
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


@Composable
fun RatingBar(rating: Int, onRatingChange: (Int) -> Unit, modifier: Modifier = Modifier)
{

    val ratingText = when (rating) {
        1 -> "Horrible"
        2 -> "Mala"
        3 -> "Neutro"
        4 -> "Buena"
        5 -> "Excelente"
        else -> ""
    }
    Row(
        modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Row(verticalAlignment = Alignment.CenterVertically) {
            for (i in 1..5) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Estrella $i",
                    tint = if (i <= rating) Color(0xFFFFB000) else Color.Gray,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onRatingChange(i) }
                )
            }
        }

        Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
            Text(ratingText)
        }

    }
}

@Composable
fun ResenaScreen(modifier: Modifier = Modifier) {
    var comida by remember { mutableStateOf(0) }
    var servicio by remember { mutableStateOf(0) }
    var ambiente by remember { mutableStateOf(0) }
    var comentario by remember { mutableStateOf("") }
    var etiquetas by remember { mutableStateOf(setOf<String>()) }
    var publicada by remember { mutableStateOf(false) }

    val general by remember {
        derivedStateOf {
            (comida + servicio + ambiente).toDouble() / 3.0
        }
    }
    val puedePublicar by remember {
        derivedStateOf {
            comida >= 1 && servicio >= 1 && ambiente >= 1 && comentario.trim().length > 20
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("<- Escribe tu reseña")

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("Restaurante El Marino")
            Text("Escribe tu reseña")
        }

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("Comida")
            RatingBar(comida, { cambio -> comida = cambio })

            Text("Servicio")
            RatingBar(servicio,{ cambio -> servicio = cambio })

            Text("Ambiente")
            RatingBar(ambiente, { cambio -> ambiente = cambio })
        }

        ResumenResena(
            general = general,
            comentario = comentario,
            onComentarioChange = { cambio -> comentario = cambio },
            etiquetas = etiquetas,
            onEtiquetasChange = { nuevas -> etiquetas = nuevas }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                publicada = true
                comida = 0
                ambiente = 0
                servicio = 0
                comentario = ""
                etiquetas = setOf<String>() },
            modifier = Modifier.fillMaxWidth(),
            enabled = puedePublicar
        ) {
            Text("Publicar")
        }
    }
}

@Composable
fun ComentarioField(
    texto: String,
    onChange: (String) -> Unit,
    maximo: Int = 200
) {
    val colorAlerta = if (texto.length > 180) Color.Red else Color.Unspecified

    OutlinedTextField(
        value = texto,
        onValueChange = { nuevoTexto ->
            if (nuevoTexto.length <= maximo) {
                onChange(nuevoTexto)
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = colorAlerta,
            unfocusedTextColor = colorAlerta
        ),
        supportingText = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${texto.length}/$maximo",
                    color = colorAlerta
                )
            }
        },
        isError = texto.length > maximo,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Comentario") }
    )
}
@Composable
fun ResumenResena(general: Double, comentario: String, onComentarioChange: (String) -> Unit,  etiquetas: Set<String>, onEtiquetasChange: (Set<String>) -> Unit) {
    val df = DecimalFormat("0.0", DecimalFormatSymbols(Locale.US))

    Box(
        Modifier.fillMaxWidth().padding(2.dp).border(
            border = BorderStroke(1.dp, color = Color.LightGray), CircleShape
        ), Alignment.Center
    ) {
        Row(
            Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Calificacion general")
            Text(df.format(general))
        }
    }
    Box(Modifier.fillMaxWidth()) {
        ComentarioField(comentario, onComentarioChange)
    }
    val opcionesEtiquetas = remember {
        listOf("Familiar", "Económico", "Rápido", "Romántico", "Vegetariano")
    }

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        opcionesEtiquetas.forEach { etiqueta ->
            val seleccionada = etiqueta in etiquetas

            FilterChip(
                selected = seleccionada,
                onClick = {
                    val actualizado = if (seleccionada) {
                        etiquetas - etiqueta
                    } else {
                        etiquetas + etiqueta
                    }
                    onEtiquetasChange(actualizado)
                },
                label = { Text(etiqueta) },
                leadingIcon = if (seleccionada) {
                    {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null,
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else null
            )
        }
    }
}

