package comtest.dandeliontest.todotest.model.models.task

data class RemindersWithPendingIntent(
    val reminders: List<Reminder>,
    val pendingIntent: ParcelablePendingIntent?
)