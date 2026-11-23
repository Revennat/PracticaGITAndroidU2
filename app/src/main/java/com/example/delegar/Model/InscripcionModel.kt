package com.example.delegar.Model

import android.util.Patterns
import kotlin.text.toIntOrNull


data class InscripcionModel(
    val nombre: String = "",
    val correo: String = "",
    val edad: String = "",
    val tallerSeleccionado: String = "Seleccione Taller",
    val click: Boolean = false,
    val expanded: Boolean = false,
    val textWidth: Int = 0
) {
    val talleresDisponibles = listOf<String>("Fotografia", "Guitarra", "Pintura")
    val esErrorTaller = tallerSeleccionado.trim().isNotEmpty()

    val formularioValido = validarNombre(nombre) && validarCorreo(correo) && validarEdad(edad) && esErrorTaller


    fun onNombre(nuevoNombre: String): InscripcionModel = copy(nombre = nuevoNombre)
    fun validarNombre(nombre: String): Boolean{
        return nombre.length > 3
    }
    fun onCorreo(nuevoCorreo: String): InscripcionModel = copy(correo = nuevoCorreo)
    fun validarCorreo(correo: String): Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }
    fun onEdad(nuevoEdad: String): InscripcionModel = copy(edad = nuevoEdad)
    fun validarEdad(edad: String): Boolean{
        return (edad.toIntOrNull() ?: 0) in 12..99
    }

    fun onTextWidth(widtth: Int): InscripcionModel = copy(textWidth= widtth)
    fun onClick(): InscripcionModel = copy(click = true )
    fun onChange(cambio: String): InscripcionModel = copy(
        tallerSeleccionado= cambio,
        click= true
    )
    fun onChangeDimiss(cambio: String): InscripcionModel = copy(
        tallerSeleccionado= cambio,
        click= false
    )

    fun onExpanded(): InscripcionModel = copy(expanded = true)
    fun onDimiss(): InscripcionModel = copy(expanded = false)
    fun onEnviar(): InscripcionModel = copy(
        nombre = "",
        correo = "",
        edad = "",
        tallerSeleccionado = ""
    )

}