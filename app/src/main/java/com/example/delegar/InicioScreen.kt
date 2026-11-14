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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import com.example.delegar.Componentes.Micard
import kotlin.collections.listOf

data class Producto(val nombre: String, val precio: Double)

@Composable
fun CarritoScreen(modifier: Modifier) {
    val catalogo = remember { listOf(
        Producto("Frijol 1 Kg", 25.00),
        Producto("Leche 1 L", 27.50),
        Producto("Tortillas 1 Kg", 27.00),
        Producto("Huevo 12 pz", 50.25))}
    val cantidades = remember { mutableStateListOf(0, 0, 0, 0) }

    val totalcantidad by remember {
        derivedStateOf {
            cantidades.sum()
        }
    }
    val subtotal by remember { derivedStateOf {
                catalogo.indices.sumOf { catalogo[it].precio * cantidades[it]
            }
        }
    }
    val porcentajeDescuento = if (subtotal >= 200.0) .10 else 0.0;
    val montoDescuento = subtotal * porcentajeDescuento
    val envio = if ( (subtotal - montoDescuento) >= 150.0) 0.0 else 25.0
    val total = (subtotal - montoDescuento) + envio;

    Box(modifier.fillMaxSize().background(Color.White).wrapContentHeight().padding(14.dp)){
        Row(modifier.wrapContentHeight().fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Tienda Don Pepe", style = MaterialTheme.typography.titleLarge, color = Color.Black)
            Text("6", color = Color.Black)
        }
        Column(modifier.fillMaxWidth()) {
            Column(modifier = modifier.fillMaxWidth()) {
                ProductoItem(catalogo.get(0), cantidades.get(0), {cambio -> cantidades[0] = cambio})
                HorizontalDivider(Modifier.height(24.dp))
                ProductoItem(catalogo.get(1), cantidades.get(1), {cambio -> cantidades[1] = cambio})
                HorizontalDivider(Modifier.height(24.dp))
                ProductoItem(catalogo.get(2), cantidades.get(2), {cambio -> cantidades[2] = cambio})
                HorizontalDivider(Modifier.height(24.dp))
                ProductoItem(catalogo.get(3), cantidades.get(3), {cambio -> cantidades[3] = cambio})
                HorizontalDivider(Modifier.height(24.dp))
                ResumenCarrito(totalcantidad, subtotal, porcentajeDescuento, envio, total)
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton({
                    cantidades[0] = 0
                    cantidades[1] = 0
                    cantidades[2] = 0
                    cantidades[3] = 0
                }) {
                    Text("Vaciar Carrito", color = Color.Black)
                }
                Button({
                    cantidades[0] = 0
                    cantidades[1] = 0
                    cantidades[2] = 0
                    cantidades[3] = 0
                }){
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

