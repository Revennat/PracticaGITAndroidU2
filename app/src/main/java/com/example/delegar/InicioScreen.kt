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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment

import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.input.KeyboardType
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


data class Gasto(val id: Int, val concepto: String,
                 val monto: Double, val categoria: Categoria)

enum class Categoria{
    Comida, Transporte, Renta, Ocio, Otros
}

@Composable
fun PresupuestoScreen(modifier: Modifier = Modifier) {
    var ingresoTexto by remember { mutableStateOf("4500") }
    val gastos = remember { mutableStateListOf<Gasto>() }
    var siguienteId by remember { mutableIntStateOf(1) }

    val regexEntero = Regex("^\\d*$")



    val gasto by remember {
        derivedStateOf {
            gastos.sumOf { it.monto }
        }
    }

    val total by remember {
        derivedStateOf {
            ingresoTexto.toIntOrNull() ?: 0
        }
    }

    val saldoRestante by remember {
        derivedStateOf {
            (total - gasto).coerceAtLeast(0.0).toInt()
        }
    }

    val porcentaje by remember {
        derivedStateOf {
            if (total > 0) {
                ((gasto * 100) / total).coerceIn(0.0, 100.0).toInt()
            } else {
                0
            }
        }
    }

    val gastoPorCategoria by remember {
        derivedStateOf {
            gastos
                .groupBy { it.categoria }
                .mapValues { (_, lista) -> lista.sumOf { it.monto } }
        }
    }

    val categoriaTop by remember {
        derivedStateOf {
            gastoPorCategoria.maxByOrNull { (_, totalCategoria) -> totalCategoria }
        }
    }

    val nombreMayorGasto = categoriaTop?.key?.name ?: "Ninguno"
    val montoMayorGasto = categoriaTop?.value ?: 0
    val progreso = (porcentaje / 100f).coerceIn(0f, 1f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Ingreso mensual
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Mi Presupuesto",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = ingresoTexto,
                onValueChange = { cambio ->
                    if (cambio.matches(regexEntero)) {
                        ingresoTexto = cambio
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                prefix = { Text("$ ") },
                label = { Text("Ingreso Mensual") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 2. Tarjeta de resumen
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF8DC)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Gastado",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF5F5B56)
                        )
                        Text(
                            text = "$$gasto",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1F1D1A)
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Saldo",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF5F5B56)
                        )
                        Text(
                            text = "$$saldoRestante",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                    }
                }

                LinearProgressIndicator(
                    progress = { progreso },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(CircleShape),
                    color = when {
                        progreso >= 0.99f -> Color.Red
                        progreso > 0.80f -> Color(0xFFF6A623)
                        else -> Color(0xFF2E7D32)
                    },
                    trackColor = Color(0xFFEAE5E8),
                    strokeCap = StrokeCap.Round
                )

                Text(
                    text = "$porcentaje% usado · Mayor gasto: $nombreMayorGasto ($$montoMayorGasto)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF5F5B56)
                )
            }
        }

        // 3. Formulario de nuevo gasto
        FormularioGasto(
            onAgregar = { concepto, monto, categoria ->
                gastos.add(
                    Gasto(
                        id = siguienteId++,
                        concepto = concepto,
                        monto = monto,
                        categoria = categoria
                    )
                )
            }
        )

        // 4. Lista de gastos recientes
        ListaGastos(
            gastos = gastos,
            onEliminar = { gastoEliminado ->
                gastos.remove(gastoEliminado)
            }
        )
    }
}

@Composable
fun FormularioGasto(onAgregar: (concepto: String, monto: Double, Categoria) -> Unit) {
    var concepto by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("0.0") }
    var categoria by remember { mutableStateOf(Categoria.Comida) }
    var menuAbierto by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        Text("Nuevo Gasto")
        OutlinedTextField(concepto, {cambio -> concepto = cambio}, label = {Text("Concepto")}, modifier = Modifier.fillMaxWidth())
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(monto,
                {cambio -> monto = cambio},
                label = {Text("Monto")},
                modifier = Modifier.weight(1f),
                prefix = {Text("$ ")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Box(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = categoria.name,
                    onValueChange = {},
                    readOnly = true,
                    enabled = false, // evita que abra teclado; el clic lo toma el Box
                    label = { Text("Categoría") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { menuAbierto = true }
                )

                DropdownMenu(
                    expanded = menuAbierto,
                    onDismissRequest = { menuAbierto = false }
                ) {
                    Categoria.entries.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion.name) },
                            onClick = {
                                categoria = opcion
                                menuAbierto = false
                            }
                        )
                    }
                }
            }
        }
        Button(
            {
                val montoFinal = monto.toDoubleOrNull()
                if (concepto.isNotBlank() && montoFinal != null && montoFinal >= 0.0) {
                    onAgregar(concepto.trim(), montoFinal, categoria)
                    concepto = ""
                    monto = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Agregar Gasto")
        }
    }
}

@Composable
fun ListaGastos(
    gastos: List<Gasto>,
    onEliminar: (Gasto) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Gastos recientes",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        gastos.forEach { gasto ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = gasto.concepto,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = gasto.categoria.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6E6A66)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "-$${gasto.monto}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC62828)
                    )
                    IconButton(
                        onClick = { onEliminar(gasto) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Eliminar gasto",
                            tint = Color(0xFF6E6A66),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
