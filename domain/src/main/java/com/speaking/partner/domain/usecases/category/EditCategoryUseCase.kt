package com.speaking.partner.domain.usecases.category

import com.speaking.partner.domain.baseusecases.UseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.category.CategoryRepository
import com.speaking.partner.model.models.task.Category
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class EditCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Category, Unit>(dispatcher) {
    override suspend fun execute(parameters: Category) {
        if (parameters.id == 0L) {
            categoryRepository.createCategory(parameters)
        } else {
            categoryRepository.updateCategory(parameters)
        }
    }
}