package com.world.data.di

import com.world.data.repository.DatabaseRepositoryImpl
import com.world.data.repository.NetworkRepositoryImpl
import com.world.domain.repository.DatabaseRepository
import com.world.domain.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideMisaRepository(repository: NetworkRepositoryImpl): NetworkRepository = repository

    @Provides
    @Singleton
    fun provideMisaDatabaseRepository(repository: DatabaseRepositoryImpl): DatabaseRepository = repository


}