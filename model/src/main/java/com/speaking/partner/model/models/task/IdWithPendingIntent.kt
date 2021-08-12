package com.speaking.partner.model.models.task

data class IdWithPendingIntent(
    val taskId: Long,
    val pendingIntent: ParcelablePendingIntent?
)