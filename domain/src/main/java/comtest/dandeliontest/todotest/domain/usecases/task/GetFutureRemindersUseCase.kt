package comtest.dandeliontest.todotest.domain.usecases.task

import comtest.dandeliontest.todotest.domain.baseusecases.UseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.reminder.ReminderRepository
import comtest.dandeliontest.todotest.model.models.task.Reminder
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetFutureRemindersUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Long, List<Reminder>>(dispatcher) {
    override suspend fun execute(parameters: Long): List<Reminder> =
        reminderRepository.getFutureReminders(System.currentTimeMillis(), parameters)
}

