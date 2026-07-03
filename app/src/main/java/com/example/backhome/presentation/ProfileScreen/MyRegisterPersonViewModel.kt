package com.example.backhome.presentation.ProfileScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.backhome.domain.model.Person
import com.example.backhome.domain.usecase.GetMyPersonUseCase
import com.example.backhome.domain.usecase.GetPersonUseCase
import com.example.backhome.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class MyRegisterPersonViewModel @Inject constructor(
    private val getMyPersonUseCase: GetMyPersonUseCase
) : ViewModel(){
    private val _getMyPersonState = MutableStateFlow<Result<List<Person>>>(Result.Idle)
    val getMyPersonState: StateFlow<Result<List<Person>>> = _getMyPersonState.asStateFlow()


    fun getMyPersons() {

        viewModelScope.launch {

            _getMyPersonState.value = Result.Loading

            _getMyPersonState.value = getMyPersonUseCase()

        }
    }

}