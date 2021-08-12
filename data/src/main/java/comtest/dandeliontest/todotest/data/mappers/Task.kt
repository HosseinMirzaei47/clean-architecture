package comtest.dandeliontest.todotest.data.mappers

import com.support.multicalendar.DandelionDate
import com.support.multicalendar.models.CalendarType.GREGORIAN
import comtest.dandeliontest.todotest.model.models.task.Task
import comtest.dandeliontest.todotest.model.models.task.TaskIdAndCategoryId
import comtest.dandeliontest.todotest.model_android.entities.*
import java.util.*

fun TaskEntity.toTask() = Task(
    id = taskId,
    title = title,
    description = description,
    dueDate = dueDate?.let {
        DandelionDate(it, TimeZone.getDefault(), GREGORIAN)
    },
    isDone = isDone,
    doneDate = doneDate?.let {
        DandelionDate(it, TimeZone.getDefault(), GREGORIAN)
    },
    priority = priority,
    createdAt = DandelionDate(createdAt, TimeZone.getDefault(), GREGORIAN),
    updatedAt = DandelionDate(updatedAt, TimeZone.getDefault(), GREGORIAN),
)

fun TaskWithRemindersAndCategories.toTask() = Task(
    id = taskEntity.taskId,
    title = taskEntity.title,
    description = taskEntity.description,
    dueDate = taskEntity.dueDate?.let {
        DandelionDate(it, TimeZone.getDefault(), GREGORIAN)
    },
    dueTime = taskEntity.dueTime?.let {
        DandelionDate(it, TimeZone.getDefault(), GREGORIAN)
    },
    isDone = taskEntity.isDone,
    doneDate = taskEntity.doneDate?.let {
        DandelionDate(it, TimeZone.getDefault(), GREGORIAN)
    },
    priority = taskEntity.priority,
    createdAt = DandelionDate(
        taskEntity.createdAt,
        TimeZone.getDefault(),
        GREGORIAN
    ),
    updatedAt = DandelionDate(
        taskEntity.updatedAt,
        TimeZone.getDefault(),
        GREGORIAN
    ),
    categories = categoryEntity.let {
        it.map { categoryEntity ->
            categoryEntity.toCategory()
        }
    }.toMutableList(),
    reminders = reminderEntity.let {
        it.map { reminderEntity ->
            reminderEntity.toReminder()
        }
    }.toMutableList()
)

fun TaskWithReminders.toTask() = Task(
    id = taskEntity.taskId,
    title = taskEntity.title,
    description = taskEntity.description,
    dueDate = taskEntity.dueDate?.let {
        DandelionDate(it, TimeZone.getDefault(), GREGORIAN)
    },
    dueTime = taskEntity.dueTime?.let {
        DandelionDate(it, TimeZone.getDefault(), GREGORIAN)
    },
    isDone = taskEntity.isDone,
    doneDate = taskEntity.doneDate?.let {
        DandelionDate(it, TimeZone.getDefault(), GREGORIAN)
    },
    priority = taskEntity.priority,
    createdAt = DandelionDate(
        taskEntity.createdAt,
        TimeZone.getDefault(),
        GREGORIAN
    ),
    updatedAt = DandelionDate(
        taskEntity.updatedAt,
        TimeZone.getDefault(),
        GREGORIAN
    ),
    reminders = reminderEntity.map {
        it.toReminder()
    }.toMutableList()
)

fun List<TaskWithReminders>.toTasks() = map { it.toTask() }

fun TaskStatistics.toTask() = Task(
    dueDate = dueDate?.let {
        DandelionDate(it, TimeZone.getDefault(), GREGORIAN)
    },
    isDone = isDone,
    doneDate = doneDate?.let {
        DandelionDate(it, TimeZone.getDefault(), GREGORIAN)
    },
)

fun Task.toTaskEntity() = TaskEntity(
    taskId = id,
    title = title,
    description = description,
    dueDate = dueDate?.timeInMillis,
    dueTime = dueTime?.timeInMillis,
    isDone = isDone,
    doneDate = doneDate?.timeInMillis,
    priority = priority,
    createdAt = createdAt.timeInMillis,
    updatedAt = System.currentTimeMillis(),
)

fun List<Task>.toTaskEntities() = map { it.toTaskEntity() }

fun Task.toTaskEntityWithRemindersAndCategories() = TaskWithRemindersAndCategories(
    taskEntity = TaskEntity(
        taskId = id,
        title = title,
        description = description,
        dueDate = dueDate?.timeInMillis,
        isDone = isDone,
        doneDate = doneDate?.timeInMillis,
        priority = priority,
        createdAt = createdAt.timeInMillis,
        updatedAt = updatedAt.timeInMillis,
    ),
    reminderEntity = reminders.map { it.toReminderEntity() },
    categoryEntity = categories.map { it.toCategoryEntity() }
)

fun TaskIdAndCategoryId.toTaskCategoryCrossRef() = TaskCategoryCrossRef(taskId, categoryId)