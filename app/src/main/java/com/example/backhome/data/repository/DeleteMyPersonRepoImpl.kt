package com.example.backhome.data.repository

import com.example.backhome.domain.repository.DeleteMyPersonRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.example.backhome.util.Result
import com.example.backhome.util.Result.Failure


class DeleteMyPersonRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : DeleteMyPersonRepository{
    override suspend fun deletePerson(personId: String): Result<String> {

        return try {

            val uid = FirebaseAuth.getInstance().currentUser?.uid
                ?: return Failure("User not logged in")

            firestore.collection("users")
                .document(uid)
                .collection("persons")
                .document(personId)
                .delete()
                .await()

            Result.Success("Person deleted successfully")

        } catch (e: Exception) {

            Result.Failure(e.message ?: "Delete Failed")
        }
    }

}