package com.speaking.partner.model_android.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "reminders",

    )
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val reminderId: Long,
    @ColumnInfo(index = true)
    val taskOwnerId: Long,
    val time: Long,
    val createdAt: Long,
    val updatedAt: Long,
)