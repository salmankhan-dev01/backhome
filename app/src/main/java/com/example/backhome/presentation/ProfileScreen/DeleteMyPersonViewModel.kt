package com.example.backhome.presentation.ProfileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.backhome.domain.model.Person
import com.example.backhome.domain.usecase.DeleteMyPersonUseCase
import com.example.backhome.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class DeleteMyPersonViewModel @Inject constructor(
    private val deleteMyPersonUseCase: DeleteMyPersonUseCase
) : ViewModel(){

    private val _deleteMyPersonState = MutableStateFlow<Result<String>>(Result.Idle)
    val deleteMyPersonState: StateFlow<Result<String>> = _deleteMyPersonState.asStateFlow()

    fun deletePerson(personId: String) {

        viewModelScope.launch {

            _deleteMyPersonState.value = Result.Loading

            _deleteMyPersonState.value =
                deleteMyPersonUseCase(personId)
        }
    }
}