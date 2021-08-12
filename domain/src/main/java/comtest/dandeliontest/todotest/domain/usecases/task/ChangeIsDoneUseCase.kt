package comtest.dandeliontest.todotest.domain.usecases.task

import com.support.multicalendar.DandelionDate
import com.support.multicalendar.models.CalendarType
import comtest.dandeliontest.todotest.domain.baseusecases.UseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.notification.TaskAlarmManager
import comtest.dandeliontest.todotest.domain.repositories.task.TaskRepository
import comtest.dandeliontest.todotest.model.models.task.TaskWithReminderPendingIntent
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import javax.inject.Inject

class ChangeIsDoneUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskAlarmManager: TaskAlarmManager,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<TaskWithReminderPendingIntent, Unit>(dispatcher) {
    override suspend fun execute(parameters: TaskWithReminderPendingIntent) {
        parameters.task.let { task ->
            if (!task.isDone) {
                task.reminders.forEach { reminder ->
                    if (reminder.hasValidDueTime) {
                        parameters.pendingIntent?.let { pendingIntent ->
                            taskAlarmManager.setAlarmForTask(
                                reminder,
                                pendingIntent
                            )
                        }
                    }
                }
                task.doneDate = null
            } else {
                taskAlarmManager.cancelAlarmForTask(task.id)
                task.doneDate = DandelionDate(TimeZone.getDefault(), CalendarType.GREGORIAN)
            }
            taskRepository.changeIsDoneTask(task.id, task.isDone, task.doneDate?.timeInMillis)
        }
    }
}