package com.example.backhome.data.repository

import com.example.backhome.domain.repository.AuthRepository
import com.example.backhome.util.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(result.user?.uid ?: "Login Success")
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Login Failed")
        }
    }

    override suspend fun register(email: String, password: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Result.Success(result.user?.uid ?: "Register Success")
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Register Failed")
        }
    }
}