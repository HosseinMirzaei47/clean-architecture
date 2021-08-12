package comtest.dandeliontest.todotest.model_android.entities

import androidx.room.Embedded

data class CategoryWithTaskCount(
    @Embedded val categoryEntity: CategoryEntity,
    val taskCount: Int
)