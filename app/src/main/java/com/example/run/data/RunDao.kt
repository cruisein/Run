package com.example.run.data

import androidx.room.*
import com.example.run.data.entities.Duration
import com.example.run.data.entities.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface RunDao {
    // Duration
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDuration(duration: Duration)

    @Delete
    suspend fun deleteDuration(duration: Duration)

    @Query("SELECT * FROM duration_table WHERE seconds = :seconds")
    suspend fun getDuration(seconds: Int): Duration?

    @Query("SELECT * FROM duration_table")
    fun getAllDurations(): Flow<List<Duration>>

    // Session
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: Session)

    @Query("SELECT * FROM session_table")
    fun getAllSessions(): Flow<List<Session>>
}