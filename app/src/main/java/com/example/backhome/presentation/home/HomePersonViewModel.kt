package com.example.backhome.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.backhome.domain.model.Person
import com.example.backhome.domain.usecase.GetPersonUseCase
import com.example.backhome.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class HomePersonViewModel @Inject constructor(
    private val getPersonUseCase: GetPersonUseCase
) : ViewModel(){
    private val _getPersonState = MutableStateFlow<Result<List<Person>>>(Result.Idle)
    val getPersonState: StateFlow<Result<List<Person>>> = _getPersonState.asStateFlow()

    fun getPersons() {

        viewModelScope.launch {

            _getPersonState.value = Result.Loading

            _getPersonState.value = getPersonUseCase()

        }
    }

}