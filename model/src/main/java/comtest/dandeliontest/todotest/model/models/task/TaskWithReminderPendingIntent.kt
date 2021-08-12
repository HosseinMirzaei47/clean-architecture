package comtest.dandeliontest.todotest.model.models.task

data class TaskWithReminderPendingIntent(
    val task: Task,
    val pendingIntent: ParcelablePendingIntent?
)