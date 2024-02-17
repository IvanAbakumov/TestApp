package com.world.data.di

import com.world.data.api.TestService
import com.world.data.di.quialifiers.ServerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiServicesModule {
    @Provides
    @Singleton
    fun provideMisaService(
        @ServerApi retrofit: Retrofit
    ): TestService =
        retrofit.create(TestService::class.java)
}