package com.example.backhome.domain.usecase
import com.example.backhome.util.Result


import com.example.backhome.domain.repository.InputFromRepository
import javax.inject.Inject

class FormUseCase @Inject constructor(
    private val repository: InputFromRepository
) {
    suspend operator fun invoke(
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
    ): Result<String>{
        return repository.inputform(name,age,fatherName,place,district,state,youraddress,yourphonenumber,description,type)
    }
}