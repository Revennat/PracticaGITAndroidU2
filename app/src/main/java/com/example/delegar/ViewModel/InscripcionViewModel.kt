package com.example.delegar.ViewModel

import android.text.Layout
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.lifecycle.ViewModel
import com.example.delegar.Model.InscripcionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InscripcionViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(InscripcionModel())
    val uiState: StateFlow<InscripcionModel> = _uiState.asStateFlow()

    fun onChangeNombre(nuevoNombre: String){
        _uiState.update { it.onNombre(nuevoNombre) }
    }
    fun validarNombre(nombre: String) : Boolean {
        return _uiState.value.validarNombre(nombre)
    }
    fun onChangeCorreo(nuevoCorreo: String){
        _uiState.update { it.onCorreo(nuevoCorreo) }
    }
    fun validarCorreo(correo: String) : Boolean {
        return _uiState.value.validarCorreo(correo)
    }
    fun onChangeEdad(nuevoEdad: String){
        _uiState.update { it.onEdad(nuevoEdad) }
    }
    fun validarEdad(Edad: String) : Boolean {
        return _uiState.value.validarEdad(Edad)
    }
    fun onGlobalWidht(width: LayoutCoordinates){
        _uiState.update { it.onTextWidth(width.size.width) }
    }
    fun onClick(){
        _uiState.update { it.onClick() }
    }
    fun onChangeTaller(cambio: String){
        _uiState.update { it.onChange(cambio) }
    }
    fun onChangeDimiss(cambio: String){
        _uiState.update { it.onChangeDimiss(cambio) }
    }

    fun onExpanded(){
        _uiState.update { it.onExpanded() }
    }

    fun onDimiss(){
        _uiState.update { it.onDimiss() }
    }

    fun onEnviar(){
        _uiState.update { it.onEnviar() }
    }

}