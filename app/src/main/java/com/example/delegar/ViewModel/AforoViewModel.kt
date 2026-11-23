package com.example.delegar.ViewModel



import androidx.lifecycle.ViewModel
import com.example.delegar.Model.AforoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AforoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AforoModel())
    val uiState: StateFlow<AforoModel> = _uiState.asStateFlow()

    fun registrarEntrada() {
        _uiState.update { it.conEntrada() }
    }

    fun registrarSalida() {
        _uiState.update { it.conSalida() }
    }

    fun cerrarDia() {
        _uiState.update { it.diaCerrado() }
    }
}