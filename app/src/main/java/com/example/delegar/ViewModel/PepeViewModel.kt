package com.example.delegar.ViewModel

import androidx.lifecycle.ViewModel
import com.example.delegar.Model.PepeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PepeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PepeModel())
    val uiState: StateFlow<PepeModel> = _uiState.asStateFlow()

    fun onChange(cambio: Int, indice: Int){
        _uiState.update { it.onCantidadChange(cambio, indice) }
    }
    fun onLimpiar(){
        _uiState.update { it.limpiarCantidades() }
    }
}