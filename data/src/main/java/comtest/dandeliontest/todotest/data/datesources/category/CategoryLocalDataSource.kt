package comtest.dandeliontest.todotest.data.datesources.category

import comtest.dandeliontest.todotest.model_android.entities.CategoryEntity
import comtest.dandeliontest.todotest.model_android.entities.CategoryWithTaskCount
import comtest.dandeliontest.todotest.model_android.entities.CategoryWithTasks
import kotlinx.coroutines.flow.Flow

interface CategoryLocalDataSource {
    fun createCategory(categoryEntity: CategoryEntity): Long

    fun createCategory(categoryEntities: List<CategoryEntity>)

    fun deleteCategory(categoryId: Long)

    fun deleteCategoryTaskRelations(categoryId: Long)

    fun deleteCategory(categoryEntities: List<CategoryEntity>)

    fun updateCategory(categoryEntity: CategoryEntity)

    fun getAllCategories(): Flow<List<CategoryEntity>>

    fun getAllCategoriesWithTaskCount(): Flow<List<CategoryWithTaskCount>>

    fun getCategory(id: Long): Flow<CategoryEntity>

    fun getCategoriesWithTasks(id: Long): List<CategoryWithTasks>
}