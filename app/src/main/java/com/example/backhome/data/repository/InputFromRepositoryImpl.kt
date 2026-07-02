package com.example.backhome.data.repository

import com.example.backhome.domain.repository.InputFromRepository
import com.example.backhome.util.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class InputFromRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : InputFromRepository {

    override suspend fun inputform(
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
    ): Result<String> {

        return try {

            val uid = auth.currentUser?.uid
                ?: return Result.Failure("User not logged in")

            val personMap = hashMapOf(
                "name" to name,
                "age" to age,
                "fatherName" to fatherName,
                "place" to place,
                "district" to district,
                "state" to state,
                "youraddress" to youraddress,
                "yourphonenumber" to yourphonenumber,
                "description" to description,
                "type" to type,
                "createdAt" to System.currentTimeMillis()
            )

            val document = firestore
                .collection("users")
                .document(uid)
                .collection("persons")
                .document()

            document.set(personMap).await()

            Result.Success(document.id)

        } catch (e: Exception) {
            Result.Failure(e.message ?: "Failed to submit form")
        }
    }
}