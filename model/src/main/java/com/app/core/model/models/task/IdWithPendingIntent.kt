package com.app.core.model.models.task

data class IdWithPendingIntent(
    val taskId: Long,
    val pendingIntent: ParcelablePendingIntent?
)