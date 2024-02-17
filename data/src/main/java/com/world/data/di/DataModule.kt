package com.world.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        NetworkModule::class,
        RepositoriesModule::class,
        DatabaseModule::class
    ]
)
class DataModule