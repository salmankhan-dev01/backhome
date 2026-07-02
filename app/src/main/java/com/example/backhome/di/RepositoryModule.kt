package com.example.backhome.di

import com.example.backhome.data.repository.AuthRepositoryImpl
import com.example.backhome.data.repository.GetPersonRepositoryImpl
import com.example.backhome.data.repository.InputFromRepositoryImpl
import com.example.backhome.domain.repository.AuthRepository
import com.example.backhome.domain.repository.GetPersonRepository
import com.example.backhome.domain.repository.InputFromRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindInputFromRepository(
        impl: InputFromRepositoryImpl
    ): InputFromRepository

    @Binds
    abstract fun bindGetPersonRepository(
        impl: GetPersonRepositoryImpl
    ): GetPersonRepository
}