package com.example.delegar.Model

data class CuentaModel (
            var Valor: String = "0.00",
            var Porcentaje: Int = 10,
            var Personas: Int = 1,
            var UseOtro: Boolean = false,
            var Redondeado: Boolean = false
){
    val Propina
        get() = (Valor.toDoubleOrNull() ?: 0.0) * (Porcentaje.toDouble() / 100)

    val Total
        get() = (Valor.toDoubleOrNull() ?: 0.0) + Propina

    val Porpersona
        get() = Total/Personas

    fun onValor(cambio: String): CuentaModel {
        val esValido = cambio.matches(Regex("^\\d*\\.?\\d*$"))
        return if (esValido) {
            copy(Valor = cambio)
        } else {
            this
        }
    }

    fun onPorcentaje(cambio: Int): CuentaModel =
        copy(Porcentaje = cambio)

    fun onUseOtro(cambio: Boolean): CuentaModel =
        copy(UseOtro = cambio)

    fun onPersonjas(cambio: Int): CuentaModel =
        copy(Personas= cambio)

    fun onChecked(cambio: Boolean): CuentaModel =
        copy(Redondeado= cambio)

}
