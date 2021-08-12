package com.speaking.partner.model.models.task

data class TaskWithReminderPendingIntent(
    val task: Task,
    val pendingIntent: ParcelablePendingIntent?
)