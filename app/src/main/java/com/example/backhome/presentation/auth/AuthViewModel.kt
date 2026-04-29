package com.example.backhome.presentation.auth


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.backhome.domain.usecase.LoginUseCase
import com.example.backhome.domain.usecase.RegisterUseCase
import com.example.backhome.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<Result<String>>(Result.Idle)
    val authState: StateFlow<Result<String>> = _authState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Result.Loading
            _authState.value = loginUseCase(email, password)
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Result.Loading
            _authState.value = registerUseCase(email, password)
        }
    }
}