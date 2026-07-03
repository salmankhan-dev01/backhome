package com.example.backhome.data.repository

import android.util.Log
import com.example.backhome.domain.model.Person
import com.example.backhome.domain.repository.GetMyPersonRepository
import com.example.backhome.util.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetMyPersonRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : GetMyPersonRepository {

    override suspend fun getPersons(): Result<List<Person>> {

        return try {

            val snapshot = firestore
                .collectionGroup("persons")
                .get()
                .await()

            Log.d("Firestore", "Persons = ${snapshot.size()}")

            val personList = snapshot.documents.map { document ->

                Person(
                    name = document.getString("name") ?: "",
                    age = document.getString("age") ?: "",
                    fatherName = document.getString("fatherName") ?: "",
                    place = document.getString("place") ?: "",
                    district = document.getString("district") ?: "",
                    state = document.getString("state") ?: "",
                    youraddress = document.getString("youraddress") ?: "",
                    yourphonenumber = document.getString("yourphonenumber") ?: "",
                    description = document.getString("description") ?: "",
                    type = document.getString("type") ?: "Missing"
                )
            }

            Result.Success(personList)

        } catch (e: Exception) {

            Log.e("Firestore", "Error", e)

            Result.Failure(e.message ?: "Failed")
        }
    }}