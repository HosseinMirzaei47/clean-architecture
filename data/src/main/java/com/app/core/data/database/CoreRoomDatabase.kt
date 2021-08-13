package com.app.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.core.model_android.entities.SampleEntity

@Database(
    entities = [
        SampleEntity::class,
    ],
    version = 2,
    exportSchema = false
)
abstract class CoreRoomDatabase : RoomDatabase(), CoreDatabase