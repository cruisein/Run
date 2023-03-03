package com.example.run.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.run.data.entities.Duration
import com.example.run.data.entities.Session

@Database(
    entities = [Duration::class, Session::class],
    version = 1,
    exportSchema = false,
)
abstract class RunDatabase : RoomDatabase() {
    abstract fun runDao(): RunDao
}