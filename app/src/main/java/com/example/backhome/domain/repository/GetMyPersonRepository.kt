package com.example.backhome.domain.repository

import com.example.backhome.domain.model.Person
import com.example.backhome.util.Result

interface GetMyPersonRepository {

    suspend fun getPersons(): Result<List<Person>>

}