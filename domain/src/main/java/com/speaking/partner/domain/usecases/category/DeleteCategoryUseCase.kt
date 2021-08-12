package com.speaking.partner.domain.usecases.category

import com.speaking.partner.domain.baseusecases.UseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.category.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Long, Unit>(dispatcher) {
    override suspend fun execute(parameters: Long) {
        parameters.let {
            categoryRepository.deleteCategory(parameters)
            categoryRepository.deleteCategoryTaskRelations(parameters)
        }
    }
}