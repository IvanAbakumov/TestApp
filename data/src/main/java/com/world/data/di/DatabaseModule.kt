package com.world.data.di

import android.content.Context
import androidx.room.Room
import com.world.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "databasev1"
    ).build()


//    @Provides
//    fun provideDao(database: AppDatabase): GeneralModelDao {
//        return database.generalModelDao()
//    }
}