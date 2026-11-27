package com.example.delegar.View

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delegar.ModelView.CuentaModelView
import java.text.DecimalFormat
import kotlin.math.ceil

@Composable
fun CuentaScreen(modifier: Modifier, viewModel: CuentaModelView = viewModel()){

    val uiState by viewModel.uiState.collectAsState()

    val pagoPorPersonaRedondeado = ceil(uiState.Porpersona)
    val sobrante = if (uiState.Redondeado) (pagoPorPersonaRedondeado * uiState.Personas) - uiState.Total else 0.0

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
                    value = uiState.Valor,
                    onValueChange = {viewModel.onValorChange(it)},
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
                    porcentaje = uiState.Porcentaje,
                    usaOtro = uiState.UseOtro,
                    onPorcentajeChange = {viewModel.onPorcentajeChange(it)},
                    onOtroChange = {viewModel.onUseOtroChange(it)}
                )

                StepperPersonas(
                    personas = uiState.Personas,
                    onChange = {viewModel.onPersonasChange(it)}
                )
                Row(
                    modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Redondear por persona?")
                    Switch(checked = uiState.Redondeado, onCheckedChange = { viewModel.onCheckedChange(it)})
                }
                ResultadoCuenta(uiState.Propina, uiState.Total, uiState.Porpersona, sobrante = sobrante )

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
