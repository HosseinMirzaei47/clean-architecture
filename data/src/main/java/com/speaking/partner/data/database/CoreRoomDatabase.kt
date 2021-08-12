package com.speaking.partner.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.speaking.partner.model_android.entities.SampleEntity

@Database(
    entities = [
        SampleEntity::class,
    ],
    version = 2,
    exportSchema = false
)
abstract class CoreRoomDatabase : RoomDatabase(), CoreDatabase