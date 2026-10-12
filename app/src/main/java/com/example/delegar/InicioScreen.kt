package com.example.delegar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class InicioScreen {

}


@Composable
fun InicioScreen(modfifer: Modifier){
    var x by remember { mutableStateOf("") }
    var y by remember { mutableStateOf("") }
    var r by remember { mutableStateOf(0) }
    Column(modfifer) {
        TextField(
            value = x,
            onValueChange = { numeroIngresado -> x = numeroIngresado },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = y,
            onValueChange = { numeroingresor -> y = numeroingresor },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(onClick = {
            val numX = x.trim().toIntOrNull() ?: 0
            val numY = y.trim().toIntOrNull() ?: 0
            r = numX + numY
        }) {

            Text("Sumar")
        }

        Text(text = r.toString())
    }

    //suma dos numeros
}
