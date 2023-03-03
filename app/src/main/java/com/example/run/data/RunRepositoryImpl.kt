package com.example.run.data

import com.example.run.data.entities.Duration
import com.example.run.data.entities.Session
import kotlinx.coroutines.flow.Flow

class RunRepositoryImpl(
    private val dao: RunDao
) : RunRepository {
    override suspend fun insertDuration(duration: Duration) {
        dao.insertDuration(duration)
    }

    override suspend fun deleteDuration(duration: Duration) {
        dao.deleteDuration(duration)
    }

    override suspend fun getDuration(minutes: Int): Duration? {
        return dao.getDuration(minutes)
    }

    override fun getAllDurations(): Flow<List<Duration>> {
        return dao.getAllDurations()
    }

    override suspend fun insertSession(session: Session) {
        dao.insertSession(session)
    }

    override fun getAllSessions(): Flow<List<Session>> {
        return dao.getAllSessions()
    }
}