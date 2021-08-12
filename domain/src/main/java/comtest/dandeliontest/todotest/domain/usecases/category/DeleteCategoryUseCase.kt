package comtest.dandeliontest.todotest.domain.usecases.category

import comtest.dandeliontest.todotest.domain.baseusecases.UseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.category.CategoryRepository
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