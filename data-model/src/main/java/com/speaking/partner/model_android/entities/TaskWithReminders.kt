package com.speaking.partner.model_android.entities

import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithReminders(
    @Embedded val taskEntity: TaskEntity,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "taskOwnerId"
    )
    val reminderEntity: List<ReminderEntity>
)