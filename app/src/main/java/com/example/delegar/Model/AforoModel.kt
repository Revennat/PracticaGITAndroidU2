package com.example.delegar.Model

data class AforoModel(
    val actuales: Int = 0,
    val visitasDelDia: Int = 0,
    val aforoMax: Int = 50
) {
    val porcentaje: Float
        get() = if (aforoMax > 0) actuales / aforoMax.toFloat() else 0f

    fun conEntrada(): AforoModel =
        if (actuales < aforoMax) {
            copy(actuales = actuales + 1, visitasDelDia = visitasDelDia + 1)
        } else {
            this
        }

    fun conSalida(): AforoModel =
        if (actuales > 0) {
            copy(actuales = actuales - 1)
        } else {
            this
        }

    fun diaCerrado(): AforoModel =
        copy(actuales = 0, visitasDelDia = 0)
}
