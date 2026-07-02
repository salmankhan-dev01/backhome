package com.example.backhome.presentation.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.backhome.domain.usecase.FormUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.backhome.util.Result


@HiltViewModel
class FormViewModel @Inject constructor(
    private val formUseCase: FormUseCase
):
    ViewModel() {

    private val _formState = MutableStateFlow<Result<String>>(Result.Idle)
    val formState: StateFlow<Result<String>> = _formState.asStateFlow()

    fun inputform(
        name: String,
        age: String,
        fatherName: String,
        place: String,
        district: String,
        state: String,
        youraddress: String,
        yourphonenumber: String,
        description: String,
        type: String
    ){
        viewModelScope.launch {
            _formState.value= Result.Loading
            _formState.value= formUseCase(
                name,
                age,
                fatherName,
                place,
                district,
                state,
                youraddress,
                yourphonenumber,
                description,
                type
            )
        }
    }
}