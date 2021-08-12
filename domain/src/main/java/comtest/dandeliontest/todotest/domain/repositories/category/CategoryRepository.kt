package comtest.dandeliontest.todotest.domain.repositories.category

import comtest.dandeliontest.todotest.model.models.task.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun createCategory(category: Category): Long

    fun createCategory(categories: List<Category>)

    fun deleteCategory(categoryId: Long)

    fun deleteCategoryTaskRelations(categoryId: Long)

    fun deleteCategory(categories: List<Category>)

    fun updateCategory(category: Category)

    fun getAllCategories(): Flow<List<Category>>

    fun getAllCategoriesWithTaskCount(): Flow<List<Category>>

    fun getCategory(id: Long): Flow<Category>

    fun getCategoryWithTasks(id: Long): List<Category>
}