package com.example.backhome.domain.repository
import com.example.backhome.util.Result

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<String>
    suspend fun register(email: String, password: String): Result<String>
}