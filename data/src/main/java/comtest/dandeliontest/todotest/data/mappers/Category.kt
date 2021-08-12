package comtest.dandeliontest.todotest.data.mappers

import com.support.multicalendar.DandelionDate
import com.support.multicalendar.models.CalendarType.GREGORIAN
import comtest.dandeliontest.todotest.model.models.task.Category
import comtest.dandeliontest.todotest.model_android.entities.CategoryEntity
import comtest.dandeliontest.todotest.model_android.entities.CategoryWithTaskCount
import java.util.*

fun CategoryEntity.toCategory() = Category(
    id = categoryId,
    title = title,
    color = color,
    createdAt = DandelionDate(createdAt, TimeZone.getDefault(), GREGORIAN),
    updatedAt = DandelionDate(updatedAt, TimeZone.getDefault(), GREGORIAN),
)

fun List<CategoryEntity>.toCategories() = map { it.toCategory() }

fun CategoryWithTaskCount.toCategory() = Category(
    id = categoryEntity.categoryId,
    title = categoryEntity.title,
    color = categoryEntity.color,
    taskCount = taskCount,
    createdAt = DandelionDate(categoryEntity.createdAt, TimeZone.getDefault(), GREGORIAN),
    updatedAt = DandelionDate(categoryEntity.updatedAt, TimeZone.getDefault(), GREGORIAN),
)

fun Category.toCategoryEntity() = CategoryEntity(
    categoryId = id,
    title = title,
    color = color,
    createdAt = createdAt.timeInMillis,
    updatedAt = System.currentTimeMillis(),
)

fun List<Category>.toCategoryEntities() = map { it.toCategoryEntity() }