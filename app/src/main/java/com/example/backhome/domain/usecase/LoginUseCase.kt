package com.example.backhome.domain.usecase

import com.example.backhome.domain.repository.AuthRepository
import javax.inject.Inject
import com.example.backhome.util.Result


class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<String> {
        return repository.login(email, password)
    }
}