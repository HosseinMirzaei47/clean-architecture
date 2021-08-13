package com.app.core.model_android.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
)
data class SampleEntity(
    @PrimaryKey(autoGenerate = true) val sampleId: Long
)