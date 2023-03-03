package com.example.run.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "duration_table")
data class Duration(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "seconds") val seconds: Int,
)
