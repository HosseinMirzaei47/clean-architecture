package com.app.core.model.models.task

data class RemindersWithPendingIntent(
    val reminders: List<Reminder>,
    val pendingIntent: ParcelablePendingIntent?
)