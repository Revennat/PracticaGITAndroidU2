package com.example.delegar

import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.delegar.Componentes.Micard

enum class Interlineado(val factor: Float) {
    COMPACTO(1.0f), NORMAL(1.3f), AMPLIO(1.6f)
}

@Composable
fun LecturaScreen(modifier: Modifier){
    var tamano by remember { mutableStateOf(22f) }
    val tamanoint =
    Column(
        modifier.fillMaxSize().padding(5.dp),
    ) {
        Row(
            modifier.fillMaxWidth().padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Text("<-", fontSize = 20.sp)
            Text("Ajustes de Lectura", fontSize = 15.sp)
        }
        Box(
            modifier.fillMaxWidth().height(300.dp).background(color = Color(0xFFF3EEF6), shape = RoundedCornerShape(18.dp)),
            contentAlignment = Alignment.Center
        ){
            Column(modifier.padding(10.dp)) {
                Row(modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Tamanio de letra", fontSize = 16.sp)
                    Text("${tamano.toInt()} sp", fontSize = 18.sp)
                }

                Slider(
                    value = tamano,
                    onValueChange = {nuevoTamano -> tamano = nuevoTamano },
                    valueRange = 12f..36f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF6750A4),
                        activeTrackColor = Color(0xFF6750A4),
                        inactiveTrackColor = Color(0xFFE8DEF8)),
                    modifier = Modifier.padding(5.dp))

                Row(modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("A", fontSize = 16.sp, color = Color.Gray)
                    Text("A", fontSize = 24.sp, color = Color.Gray)
                }

                Spacer(modifier.height(14.dp))

                Row(
                    modifier.fillMaxWidth()
                ) {

                }

            }

        }
    }



}

@Composable
fun ControlesLectura(
    tamano: Float, onTamanoChange: (Float) -> Unit,
    interlineado: Interlineado, onInterlineadoChange: (Interlineado) -> Unit,
    modoOscuro: Boolean, onModoOscuroChange: (Boolean) -> Unit,
    usaSerif: Boolean, onUsaSerifChange: (Boolean) -> Unit
){

}

@Composable
fun VistaPrevia(texto: String, tamano: Float, interlineado: Interlineado, modoOscuro: Boolean, usaSerif: Boolean)
{

}