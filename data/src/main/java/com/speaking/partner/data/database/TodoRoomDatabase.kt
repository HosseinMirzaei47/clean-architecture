package com.speaking.partner.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.speaking.partner.model_android.entities.CategoryEntity
import com.speaking.partner.model_android.entities.ReminderEntity
import com.speaking.partner.model_android.entities.TaskCategoryCrossRef
import com.speaking.partner.model_android.entities.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        CategoryEntity::class,
        ReminderEntity::class,
        TaskCategoryCrossRef::class,
    ],
    version = 2,
    exportSchema = false
)
abstract class TodoRoomDatabase : RoomDatabase(), TodoDatabase