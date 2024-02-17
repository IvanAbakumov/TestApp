package com.world.test.di

import android.app.Activity
import com.world.data.di.MainActivityQualifier
import com.world.test.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ActivityModule {
    @ExperimentalSerializationApi
    @Singleton
    @Provides
    @MainActivityQualifier
    internal fun mainActivity(): Activity = MainActivity()
}