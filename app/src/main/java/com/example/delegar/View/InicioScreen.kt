package com.example.delegar.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delegar.Model.Producto
import com.example.delegar.ViewModel.PepeViewModel
import kotlin.collections.listOf



@Composable
fun CarritoScreen(modifier: Modifier, viewModel: PepeViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    Box(modifier.fillMaxWidth().background(Color.White).padding(14.dp)){

        Column(modifier.fillMaxWidth()) {
            Row(modifier.fillMaxWidth().wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Tienda Don Pepe", style = MaterialTheme.typography.titleLarge, color = Color.Black)
                Text("6", color = Color.Black)
            }
            Column(modifier = modifier.fillMaxWidth()) {
                ProductoItem(uiState.catalogo.get(0), uiState.cantidades.get(0), {viewModel.onChange(it, 0)})
                HorizontalDivider(Modifier.height(24.dp))
                ProductoItem(uiState.catalogo.get(1), uiState.cantidades.get(1), {viewModel.onChange(it, 1)})
                HorizontalDivider(Modifier.height(24.dp))
                ProductoItem(uiState.catalogo.get(2), uiState.cantidades.get(2), {viewModel.onChange(it, 2)})
                HorizontalDivider(Modifier.height(24.dp))
                ProductoItem(uiState.catalogo.get(3), uiState.cantidades.get(3), {viewModel.onChange(it, 3)})
                HorizontalDivider(Modifier.height(24.dp))
                ResumenCarrito(uiState.totalcantidad, uiState.subtotal, uiState.porcentajeDescuento, uiState.envio, uiState.total)
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton({ viewModel.onLimpiar() }) {
                    Text("Vaciar Carrito", color = Color.Black)
                }
                Button({ viewModel.onLimpiar() }){
                    Text("Confirmar pedido")
                }
            }
        }


    }
}

@Composable
fun ProductoItem(producto: Producto, cantidad: Int, onCantidadChange: (Int) -> Unit)
{
    Column(
        Modifier.fillMaxWidth().background(Color(0xFFF3F4F6)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween

        )
        {
            Column(Modifier.padding(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(text = producto.nombre, style = MaterialTheme.typography.bodyLarge, color =  Color.Black)
                Text(text = "$${producto.precio.toInt()}.00 c/u", style = MaterialTheme.typography.bodyMedium, color =  Color.Black)
            }
            Row(modifier = Modifier.padding(6.dp), horizontalArrangement = Arrangement.spacedBy(6.dp) , verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(
                    onClick = { onCantidadChange(cantidad - 1) },
                    modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    enabled = cantidad > 0
                ) {
                    Text(text = "-", color = Color.Black)
                }
                Text(text = cantidad.toString(), style = MaterialTheme.typography.bodyLarge)
                OutlinedButton(
                    onClick = { onCantidadChange( cantidad + 1)},
                    modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = "+", color = Color.Black )
                }
            }
        }


    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun ResumenCarrito(articulos: Int, subtotal: Double, descuento: Double, envio: Double, total: Double)
{
    Box(modifier = Modifier.fillMaxWidth().wrapContentHeight().background(Color(0xFFE5E7EB)), contentAlignment = Alignment.Center){
        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Articulos", color = Color.Black)
                Text("Subtotal", color = Color.Black)
                Text("Descuento", color = Color.Black)
                Text("Envio", color = Color.Black)
                Text("Total", style = MaterialTheme.typography.titleLarge, color = Color.Black)
            }
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(articulos.toString(), color = Color.Black)
                Text(subtotal.toString(), color = Color.Black)
                Text(descuento.toString(), color = Color.Black)
                Text(if (envio != 25.0) "GRATIS" else "+$${envio}", color = if (envio != 25.0) Color.Green else Color.Red)
                Text(text =  if (total != 25.0) "$${total}" else "$0.00", style = MaterialTheme.typography.titleLarge, color = Color.Black)
            }
        }
    }
}

