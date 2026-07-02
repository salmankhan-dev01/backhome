package com.example.backhome.domain.usecase

import com.example.backhome.domain.model.Person
import com.example.backhome.domain.repository.GetPersonRepository
import com.example.backhome.util.Result
import javax.inject.Inject

class GetPersonUseCase @Inject constructor(
    private val repository: GetPersonRepository
) {

    suspend operator fun invoke(): Result<List<Person>> {
        return repository.getPersons()
    }

}