package com.example.delegar

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.delegar.Componentes.Micard

@Composable
fun InicioScreen(modfifer: Modifier){
    var listanumeros = remember { mutableStateListOf("") }
    var x = listanumeros.joinToString("")

    Column(modfifer.fillMaxWidth().padding(start = 50.dp, end = 50.dp)) {
        Text("Calculadora", fontSize = 25.sp)
        TextField(value = if (x.isEmpty()) "0" else x, onValueChange = {})
        Row(modfifer.fillMaxWidth()) {
            Button(onClick = { listanumeros.add("7") }) { Text( "7" )}
            Button(onClick = { listanumeros.add("8") }) { Text( "8" )}
            Button(onClick = { listanumeros.add("9") }) { Text( "9" )}
            Spacer(modfifer.width(25.dp))
            Button(onClick = {
                if(listanumeros.isNotEmpty() && !verificarOperador(listanumeros)){
                    listanumeros.add("-")
                }
            }) { Text( "-" )}
        }
        Row(modfifer.fillMaxWidth()) {
            Button(onClick = { listanumeros.add("4") }) { Text( "4" )}
            Button(onClick = { listanumeros.add("5") }) { Text( "5" )}
            Button(onClick = { listanumeros.add("6") }) { Text( "6" )}
            Spacer(modfifer.width(25.dp))
            Button(onClick = {
                if(listanumeros.isNotEmpty() && !verificarOperador(listanumeros)){
                    listanumeros.add("+")
                }
            }) { Text( "+" )}
        }
        Row(modfifer.fillMaxWidth()) {
            Button(onClick = { listanumeros.add("1")}) { Text( "1" )}
            Button(onClick = { listanumeros.add("2")}) { Text( "2" )}
            Button(onClick = { listanumeros.add("3")}) { Text( "3" )}
            Spacer(modfifer.width(25.dp))
            Button(onClick = {
                if(listanumeros.isNotEmpty() && !verificarOperador(listanumeros)){
                    listanumeros.add("*")
                }
            }) { Text( "*" )}
        }
        Row(modfifer.fillMaxWidth()) {
            Button(onClick = { listanumeros.add("0")}) { Text( "0" )}
            Spacer(modfifer.width(141.5.dp))
            Button(onClick = {
                if(listanumeros.isNotEmpty() && !verificarOperador(listanumeros)){
                    listanumeros.add("/")
                }
            }) { Text( "/" )}
        }
        Spacer(modfifer.height(16.dp))

        Button(onClick = {
            val resultado = calcularLista(listanumeros)
            listanumeros.clear()
            listanumeros.add(resultado.toString())
        }, modfifer.fillMaxWidth())
        { Text("=")}

        Button(onClick = { listanumeros.clear() }, modfifer.fillMaxWidth())
        { Text("C") }

    }
}

fun verificarOperador(list: List<String>) : Boolean{
    val operadores = listOf("+", "-", "*", "/")
    return list.any {it in operadores}
}

fun calcularLista(list: List<String>) : Double{
    val expr = list.joinToString("")

    if (expr.contains("+")) {
        val partes = expr.split("+")
        if (partes.size == 2){
            val num1 = partes[0].toDoubleOrNull() ?: 0.0
            val num2 = partes[1].toDoubleOrNull() ?: 0.0
            return num1 + num2
        }
    }

    if (expr.contains("-")) {
        val partes = expr.split("-")
        if (partes.size == 2){
            val num1 = partes[0].toDoubleOrNull() ?: 0.0
            val num2 = partes[1].toDoubleOrNull() ?: 0.0
            return num1 - num2
        }
    }

    if (expr.contains("*")) {
        val partes = expr.split("*")
        if (partes.size == 2){
            val num1 = partes[0].toDoubleOrNull() ?: 0.0
            val num2 = partes[1].toDoubleOrNull() ?: 0.0
            return num1 * num2
        }
    }

    if (expr.contains("/")) {
        val partes = expr.split("/")
        if (partes.size == 2){
            val num1 = partes[0].toDoubleOrNull() ?: 0.0
            val num2 = partes[1].toDoubleOrNull() ?: 0.0
            if (num2 == 0.0){
                return 0.0
            }
            return num1 / num2
        }
    }
    return expr.toDoubleOrNull() ?: 0.0
}

