package com.example.delegar

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class InicioScreen {

}


@Composable
fun InicioScreen(modfifer: Modifier){
    var x by remember { mutableStateOf("Ingrese otro valor") }
    var y by remember { mutableStateOf("Ingrese un valor") }
    var x1 by remember { mutableStateOf(0) }
    var y1 by remember { mutableStateOf(0) }
    var r by remember { mutableStateOf(0) }
    Column(modfifer) {
        TextField(value = x, onValueChange = {
            numeroingresor -> x = numeroingresor
            x1 = numeroingresor.toIntOrNull() ?:0
        })
        TextField(value = y, onValueChange = {
            numeroingresor -> y = numeroingresor
            y1 = numeroingresor.toIntOrNull() ?:0
        })
        Button(onClick = { r =  x1 + y1}) {
            Text("Sumar")
        }
        Text(text = r.toString())
    }

    //suma dos numeros
}
