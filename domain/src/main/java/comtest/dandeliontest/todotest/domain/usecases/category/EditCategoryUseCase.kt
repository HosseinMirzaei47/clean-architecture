package comtest.dandeliontest.todotest.domain.usecases.category

import comtest.dandeliontest.todotest.domain.baseusecases.UseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.category.CategoryRepository
import comtest.dandeliontest.todotest.model.models.task.Category
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