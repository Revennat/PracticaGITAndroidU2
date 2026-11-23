package com.example.delegar.ViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.delegar.Model.AjusteLectura

class LecturaViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(AjusteLectura())
    val uiState: StateFlow<AjusteLectura> = _uiState.asStateFlow()

    fun onChangeTamano(nuevoTamanio: Float){
        _uiState.update { it.onChangeT(nuevoTamanio) }
    }

    fun onCompcto(){
        _uiState.update { it.onCompacto() }
    }

    fun onNormal(){
        _uiState.update { it.onNormal() }
    }

    fun onAmplio(){
        _uiState.update { it.onAmplio() }
    }

    fun onCheckedmdOscuro(activo: Boolean){
        _uiState.update { it.onChekedOsc(activo) }
    }

    fun onClicktrue(){
        _uiState.update { it.onClickTrue() }
    }

    fun onClickfalse(){
        _uiState.update { it.onClickFalse() }
    }

    fun restaurar(){
        _uiState.update { it.restaurarPredeterminada() }
    }

}