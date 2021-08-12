package com.speaking.partner.data.repositories.category

import com.speaking.partner.data.datesources.category.CategoryLocalDataSource
import com.speaking.partner.data.mappers.toCategories
import com.speaking.partner.data.mappers.toCategory
import com.speaking.partner.data.mappers.toCategoryEntities
import com.speaking.partner.data.mappers.toCategoryEntity
import com.speaking.partner.domain.repositories.category.CategoryRepository
import com.speaking.partner.model.models.task.Category
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