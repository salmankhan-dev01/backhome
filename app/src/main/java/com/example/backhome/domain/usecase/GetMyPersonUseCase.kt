package com.example.backhome.domain.usecase

import com.example.backhome.domain.model.Person
import com.example.backhome.domain.repository.GetMyPersonRepository
import com.example.backhome.util.Result
import javax.inject.Inject

class GetMyPersonUseCase @Inject constructor(
    private val repository: GetMyPersonRepository
) {

    suspend operator fun invoke(): Result<List<Person>> {
        return repository.getPersons()
    }

}