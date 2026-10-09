package com.example.delegar

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
fun InicioScreen(modfifer: Modifier){
    val listaNombres = listOf<String>(
        "Carlos","Emilio","Isabela","John Pork", "JSON",
        "Carlos","Emilio","Isabela","John Pork", "JSON",
        "Carlos","Emilio","Isabela","John Pork", "JSON",
        "Carlos","Emilio","Isabela","John Pork", "JSON"
    )

    LazyColumn(modfifer.fillMaxSize()) {
        items(listaNombres) { nombre-> Micard(modfifer, nombre) }
    }

    LazyColumn(modfifer) {
        item(){
            listaNombres.forEach {
                Micard(modfifer, it)
            }
        }
    }
}
