package com.example.delegar.ModelView

import androidx.lifecycle.ViewModel
import com.example.delegar.Model.CuentaModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CuentaModelView : ViewModel() {

    private val _uiState = MutableStateFlow(CuentaModel())
    val uiState = _uiState.asStateFlow()

    fun onValorChange(cambio: String){
        _uiState.update { it.onValor(cambio) }
    }

    fun onPorcentajeChange(cambio: Int){
        _uiState.update { it.onPorcentaje(cambio) }
    }

    fun onUseOtroChange(cambio: Boolean){
        _uiState.update { it.onUseOtro(cambio) }
    }

    fun onPersonasChange(cambio: Int){
        _uiState.update { it.onPersonjas(cambio) }
    }

    fun onCheckedChange(cambio: Boolean){
        _uiState.update { it.onChecked(cambio) }
    }
}