package comtest.dandeliontest.todotest.model.models.task

data class IdWithPendingIntent(
    val taskId: Long,
    val pendingIntent: ParcelablePendingIntent?
)