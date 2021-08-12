package com.speaking.partner.domain.usecases.task

import com.speaking.partner.domain.baseusecases.UseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.reminder.ReminderRepository
import com.speaking.partner.model.models.task.Reminder
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetFutureRemindersUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Long, List<Reminder>>(dispatcher) {
    override suspend fun execute(parameters: Long): List<Reminder> =
        reminderRepository.getFutureReminders(System.currentTimeMillis(), parameters)
}

