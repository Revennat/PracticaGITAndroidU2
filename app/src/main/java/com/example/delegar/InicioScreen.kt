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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import com.example.delegar.Componentes.Micard
import java.text.DecimalFormat
import kotlin.collections.listOf
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun CuentaScreen(modifier: Modifier){

    var valor by remember { mutableStateOf("0.00") }
    var porcentajef by remember { mutableStateOf(10) }
    var personas by remember { mutableStateOf(1) }
    var UseOtro by remember { mutableStateOf(false) }
    var redondeado by remember { mutableStateOf(false) }
    val propina by remember { derivedStateOf {
        val subtotal = valor.toDoubleOrNull() ?: 0.0
        subtotal * (porcentajef.toDouble() / 100.0)
        }
    }
    val total by remember { derivedStateOf {
        (valor.toDoubleOrNull() ?: 0.0) + propina
        }
    }
    val porPersona by remember { derivedStateOf {
            total / personas
        }
    }
    val pagoPorPersonaRedondeado = ceil(porPersona)
    val sobrante = if (redondeado) (pagoPorPersonaRedondeado * personas) - total else 0.0

    Box(
        Modifier.fillMaxWidth().padding(24.dp).wrapContentHeight(),
        contentAlignment = Alignment.Center
    ){
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween

            ) {
                Text("Cuenta dividida", style = MaterialTheme.typography.titleLarge)
            }
            Column(
                Modifier.fillMaxWidth().padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween

            ) {
                OutlinedTextField(
                    value = valor,
                    onValueChange =
                        {cambio ->
                        if (cambio.matches(Regex("^\\d*\\.?\\d*\\.?$"))) { valor = cambio }
                        },
                    modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    label = { Text("Monto de la cuenta") }
                )
            }

            Column(
                Modifier.fillMaxWidth().padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text("Propina")
                SelectorPropina(
                    porcentaje = porcentajef,
                    usaOtro = UseOtro,
                    onPorcentajeChange = {cambio -> porcentajef = cambio},
                    onOtroChange = {cambio -> UseOtro = cambio}
                )

                StepperPersonas(
                    personas = personas,
                    onChange = {cambio -> personas = cambio}
                )
                Row(
                    modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Redondear por persona?")
                    Switch(checked = redondeado, onCheckedChange = { cambio -> redondeado = cambio})
                }
                ResultadoCuenta(propina, total, porPersona, sobrante = sobrante )

            }
        }


    }
}

@Composable
fun SelectorPropina(porcentaje: Int, usaOtro: Boolean, onPorcentajeChange: (Int) -> Unit, onOtroChange: (Boolean) -> Unit)
{
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        FilterChip(
            selected = !usaOtro && porcentaje == 10,
            onClick =
                {
                    onPorcentajeChange(10)
                    onOtroChange(false)
                },
            label = {Text("10%")})
        FilterChip(
            selected = !usaOtro && porcentaje == 15,
            onClick =
                {
                    onPorcentajeChange(15)
                    onOtroChange(false)
                },
            label = {Text("15%")})
        FilterChip(
            selected = !usaOtro && porcentaje == 20,
            onClick =
                {
                    onPorcentajeChange(20)
                    onOtroChange(false)
                },
            label = {Text("20%")})
        FilterChip(
            selected = usaOtro,
            onClick =
                {
                    onOtroChange(true)
                },
            label = {Text("Otro")})
    }
    if (usaOtro){
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Personalizado: $porcentaje%",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Slider(
                value = porcentaje.toFloat(),
                onValueChange = { nuevoValor ->
                    onPorcentajeChange(nuevoValor.toInt())
                },
                valueRange = 1f..30f
            )
        }
    }
}

@Composable
fun StepperPersonas(personas: Int, onChange: (Int) -> Unit){
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Personas")

        Button({onChange(personas - 1)}, enabled = personas > 1){
            Text("-")
        }
        Text(personas.toString())
        Button({onChange(personas + 1)}, enabled = personas < 20){
            Text("+")
        }
    }
}

@Composable
fun ResultadoCuenta(propina: Double, total: Double, porPersona: Double, sobrante: Double?,)
{

    val formato = DecimalFormat("$0.00")
    Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()
        .background(Color.LightGray)
        .border(
            border = BorderStroke(1.dp, color = Color.LightGray),
            shape = CircleShape
        ),
        Alignment.Center,
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(100.dp),
            Alignment.CenterVertically
        ) {
          Column(
              verticalArrangement = Arrangement.SpaceBetween,
              horizontalAlignment = Alignment.Start
          ) {
              Text("Propina: ", color = Color.Black)
              Text("Total con propina: ", color = Color.Black)
              Text("Cada quien paga: ", color = Color.Black)
              Text("Sobrante: ", color = Color.Black)
          }
          Column(
              verticalArrangement = Arrangement.SpaceBetween,
              horizontalAlignment = Alignment.End
          ) {
              Text(formato.format(propina), color = Color.Black)
              Text(formato.format(total), color = Color.Black)
              Text(if (sobrante == 0.0) formato.format(porPersona) else formato.format(ceil(porPersona)), color = Color.Black)
              Text(if (sobrante == 0.0) "$0.00"  else formato.format(sobrante))
          }

        }
    }
}
