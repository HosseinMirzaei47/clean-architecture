package com.speaking.partner.model_android.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ReminderAndTask(
    @Embedded val reminderEntity: ReminderEntity,
    @Relation(
        parentColumn = "taskOwnerId",
        entityColumn = "taskId",
    )
    val taskEntity: TaskEntity,
)