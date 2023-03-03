package com.example.run.data

import com.example.run.data.entities.Duration
import com.example.run.data.entities.Session
import kotlinx.coroutines.flow.Flow

interface RunRepository {
    suspend fun insertDuration(duration: Duration)

    suspend fun deleteDuration(duration: Duration)

    suspend fun getDuration(minutes: Int): Duration?

    fun getAllDurations(): Flow<List<Duration>>

    suspend fun insertSession(session: Session)

    fun getAllSessions(): Flow<List<Session>>
}