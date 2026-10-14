package com.example.delegar

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.delegar.Componentes.Micard

@Composable
fun InicioScreen(modifier: Modifier = Modifier) {
    var x by remember { mutableStateOf("") }
    val listaNombres = remember { mutableStateListOf<String>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = x,
            onValueChange = { sv -> x = sv },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Escribe un nombre") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (x.isNotBlank()) {
                    listaNombres.add(x.trim())
                    x = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listaNombres) { nombre ->
                Micard(
                    nombre = nombre,
                    Actualizar = {
                        listaNombres.remove(nombre)
                        x = nombre
                    },
                    Eliminar = {
                        listaNombres.remove(nombre)
                    },
                    modifier = modifier
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
