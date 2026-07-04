package com.example.backhome.domain.repository

import com.example.backhome.util.Result

interface DeleteMyPersonRepository {

    suspend fun deletePerson(personId: String): Result<String>
}