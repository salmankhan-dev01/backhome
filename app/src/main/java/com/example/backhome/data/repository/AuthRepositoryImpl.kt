package com.example.backhome.data.repository

import com.example.backhome.domain.repository.AuthRepository
import com.example.backhome.util.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore

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
            val uid = result.user?.uid ?: ""

            val userMap = hashMapOf(
                "uid" to uid,
                "email" to email,
                "password" to password
            )

//            firestore.collection("users")
//                .document(uid)
//                .set(userMap)
//                .await()

            firestore.collection("users")
                .document(uid)
                .collection("login")
                .document("user")
                .set(userMap)
                .await()


            Result.Success(uid)
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Register Failed")
        }
    }
}