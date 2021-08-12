package comtest.dandeliontest.todotest.data.repositories.category

import comtest.dandeliontest.todotest.data.datesources.category.CategoryLocalDataSource
import comtest.dandeliontest.todotest.data.mappers.toCategories
import comtest.dandeliontest.todotest.data.mappers.toCategory
import comtest.dandeliontest.todotest.data.mappers.toCategoryEntities
import comtest.dandeliontest.todotest.data.mappers.toCategoryEntity
import comtest.dandeliontest.todotest.domain.repositories.category.CategoryRepository
import comtest.dandeliontest.todotest.model.models.task.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryLocalDataSource: CategoryLocalDataSource,
) : CategoryRepository {
    override fun createCategory(category: Category): Long =
        categoryLocalDataSource.createCategory(category.toCategoryEntity())

    override fun createCategory(categories: List<Category>) =
        categoryLocalDataSource.createCategory(categories.toCategoryEntities())

    override fun deleteCategory(categoryId: Long) =
        categoryLocalDataSource.deleteCategory(categoryId)

    override fun deleteCategoryTaskRelations(categoryId: Long) =
        categoryLocalDataSource.deleteCategoryTaskRelations(categoryId)

    override fun deleteCategory(categories: List<Category>) =
        categoryLocalDataSource.deleteCategory(categories.toCategoryEntities())

    override fun updateCategory(category: Category) =
        categoryLocalDataSource.updateCategory(category.toCategoryEntity())

    override fun getAllCategories(): Flow<List<Category>> =
        categoryLocalDataSource.getAllCategories().map { it.toCategories() }

    override fun getAllCategoriesWithTaskCount(): Flow<List<Category>> =
        categoryLocalDataSource.getAllCategoriesWithTaskCount()
            .map { list -> list.map { it.toCategory() } }

    override fun getCategory(id: Long): Flow<Category> =
        categoryLocalDataSource.getCategory(id).map { it.toCategory() }

    override fun getCategoryWithTasks(id: Long): List<Category> =
        categoryLocalDataSource.getCategoriesWithTasks(id).map { it.categoryEntity.toCategory() }
}