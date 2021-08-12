package com.speaking.partner.domain.usecases.category

import com.speaking.partner.domain.baseusecases.FlowUseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.category.CategoryRepository
import com.speaking.partner.model.models.task.Category
import com.speaking.partner.shared.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Long, Category>(dispatcher) {
    override fun execute(parameter: Long): Flow<Resource<Category>> =
        categoryRepository.getCategory(parameter).map {
            Resource.Success(it)
        }
}