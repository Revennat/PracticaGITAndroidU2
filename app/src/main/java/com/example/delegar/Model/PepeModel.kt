package com.example.delegar.Model

import androidx.compose.runtime.derivedStateOf

data class Producto(val nombre: String, val precio: Double)

data class PepeModel (
    val catalogo: List<Producto> = listOf<Producto>(
        Producto("Frijol 1 Kg", 25.00),
        Producto("Leche 1 L", 27.50),
        Producto("Tortillas 1 Kg", 27.00),
        Producto("Huevo 12 pz", 50.25)),
    val cantidades: List<Int> = listOf(0,0,0,0)
){
    val totalcantidad: Int
        get() =  cantidades.sum()

    val subtotal: Double
        get() =  catalogo.indices.sumOf { catalogo[it].precio * cantidades[it] }

    val porcentajeDescuento: Double
        get() =  if (subtotal >= 200.0) .10 else 0.0

    val montoDescuento: Double
        get() =  subtotal * porcentajeDescuento

    val envio : Double
        get() =  if ( (subtotal - montoDescuento) >= 150.0) 0.0 else 25.0
    val total: Double
        get()= (subtotal - montoDescuento) + envio

    fun onCantidadChange(cambio: Int, indice: Int): PepeModel{
        if (indice !in cantidades.indices) return this

        val nuevascantidades = cantidades.mapIndexed { i, actual ->
            if (i == indice) cambio.coerceAtLeast(0) else actual
        }
        return copy(cantidades = nuevascantidades)
    }
    fun limpiarCantidades(): PepeModel =
        copy(cantidades = List(cantidades.size) { 0 })
}