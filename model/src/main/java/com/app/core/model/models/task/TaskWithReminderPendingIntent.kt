package com.app.core.model.models.task

data class TaskWithReminderPendingIntent(
    val task: Task,
    val pendingIntent: ParcelablePendingIntent?
)