package com.speaking.partner.model_android.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TaskWithRemindersAndCategories(
    @Embedded val taskEntity: TaskEntity,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "taskOwnerId",
        entity = ReminderEntity::class
    )
    val reminderEntity: List<ReminderEntity>,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "categoryId",
        associateBy = Junction(TaskCategoryCrossRef::class)
    )
    val categoryEntity: List<CategoryEntity>,
)