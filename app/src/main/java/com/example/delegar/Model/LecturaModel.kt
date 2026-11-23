package com.example.delegar.Model


data class AjusteLectura(
    val tamano: Float = 22f,
    val opcion: Interlineado = Interlineado.NORMAL,
    val mdOscuro: Boolean = true,
    val serif: Boolean = false){

    fun onChangeT(nuevoTamano: Float): AjusteLectura =
        copy(tamano = nuevoTamano)

    fun onCompacto(): AjusteLectura = copy(opcion = Interlineado.COMPACTO)
    fun onNormal(): AjusteLectura = copy(opcion = Interlineado.NORMAL)
    fun onAmplio(): AjusteLectura = copy(opcion = Interlineado.AMPLIO)
    fun onChekedOsc(activo: Boolean): AjusteLectura = copy(mdOscuro = activo)
    fun onClickTrue(): AjusteLectura = copy(serif= true)
    fun onClickFalse(): AjusteLectura = copy(serif= true)
    fun restaurarPredeterminada(): AjusteLectura = copy(
        tamano = 22f,
        opcion = Interlineado.NORMAL,
        mdOscuro = true,
        serif = false
    )
}

enum class Interlineado(val factor: Float) {
    COMPACTO(1.0f), NORMAL(1.3f), AMPLIO(1.6f)
}