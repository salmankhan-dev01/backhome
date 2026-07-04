package com.example.backhome.domain.usecase

import com.example.backhome.domain.repository.DeleteMyPersonRepository
import jakarta.inject.Inject
import com.example.backhome.util.Result

class DeleteMyPersonUseCase @Inject constructor(
    private val repository: DeleteMyPersonRepository
) {

    suspend operator fun invoke(personId: String): Result<String> {

        return repository.deletePerson(personId)

    }
}