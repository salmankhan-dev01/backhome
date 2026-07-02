package com.example.backhome.domain.repository
import com.example.backhome.util.Result


interface InputFromRepository {
    suspend fun inputform(name: String,
                          age: String,
                          fatherName: String,
                          place: String,
                          district: String,
                          state: String,
                          youraddress: String,
                          yourphonenumber: String,
                          description: String,
                          type: String):
            Result<String>
}