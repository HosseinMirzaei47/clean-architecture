package comtest.dandeliontest.todotest.domain.usecases.task

import comtest.dandeliontest.todotest.domain.baseusecases.UseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.reminder.ReminderRepository
import comtest.dandeliontest.todotest.domain.repositories.task.TaskRepository
import comtest.dandeliontest.todotest.model.models.task.Category
import comtest.dandeliontest.todotest.model.models.task.Task
import comtest.dandeliontest.todotest.model.models.task.TaskIdAndCategoryId
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