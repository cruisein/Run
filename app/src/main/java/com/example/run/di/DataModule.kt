package com.example.run.di

import android.content.Context
import androidx.room.Room
import com.example.run.data.RunDatabase
import com.example.run.data.RunRepository
import com.example.run.data.RunRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRunRepository(database: RunDatabase): RunRepository {
        return RunRepositoryImpl(database.runDao())
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideRunDatabase(@ApplicationContext context: Context): RunDatabase {
        return Room.databaseBuilder(
            context,
            RunDatabase::class.java,
            "run_database"
        ).build()
    }
}