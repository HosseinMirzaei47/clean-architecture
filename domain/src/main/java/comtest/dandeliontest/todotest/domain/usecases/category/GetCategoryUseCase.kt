package comtest.dandeliontest.todotest.domain.usecases.category

import comtest.dandeliontest.todotest.domain.baseusecases.FlowUseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.category.CategoryRepository
import comtest.dandeliontest.todotest.model.models.task.Category
import comtest.dandeliontest.todotest.shared.resource.Resource
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