package com.speaking.partner.domain.usecases.task

import com.speaking.partner.domain.baseusecases.UseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.reminder.ReminderRepository
import com.speaking.partner.domain.repositories.task.TaskRepository
import com.speaking.partner.model.models.task.Category
import com.speaking.partner.model.models.task.Task
import com.speaking.partner.model.models.task.TaskIdAndCategoryId
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class EditTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val reminderRepository: ReminderRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : UseCase<Task, Long>(dispatcher) {
    override suspend fun execute(parameters: Task): Long {

        parameters.let { task ->
            if (task.id == 0L) {
                val taskId = taskRepository.createTask(task)
                task.id = taskId
            } else {
                taskRepository.deleteTaskCategoryRelations(task.id)
                taskRepository.updateTask(task)
                reminderRepository.deleteAllRemindersOfTask(task.id)
            }

            task.reminders.forEach { reminder ->
                reminder.taskId = task.id
            }

            task.categories.forEach { category: Category ->
                taskRepository.createTaskCategoryRelations(
                    TaskIdAndCategoryId(
                        task.id,
                        category.id
                    )
                )
            }

            reminderRepository.createReminder(task.reminders)
            return task.id
        }
    }
}