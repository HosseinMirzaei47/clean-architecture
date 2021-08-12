package comtest.dandeliontest.todotest.domain.baseusecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import comtest.dandeliontest.todotest.domain.utils.readServerError
import comtest.dandeliontest.todotest.shared.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * Executes business logic in its execute method and keep posting updates to the Resource as
 * [Resource<R>].
 * Handling an exception (emit [Resource.Error] to the Resource) is the subclasses's responsibility.
 */
abstract class MediatorUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    private val resource = MediatorLiveData<Resource<R>>()

    // Make this as open so that mock instances can mock this method
    open fun observe(): MediatorLiveData<Resource<R>> {
        return resource
    }

    /** Executes the use case asynchronously and returns a [Resource].
     *
     * @return a [Resource].
     *
     * @param parameters the input parameters to run the use case with
     */
    suspend operator fun invoke(parameter: P) {
        try {
            // Moving all use case's executions to the injected dispatcher
            // In production code, this is usually the Default dispatcher (background thread)
            // In tests, this becomes a TestCoroutineDispatcher
            val observable = withContext(coroutineDispatcher) { execute(parameter) }
            resource.removeSource(observable)
            resource.addSource(observable) {
                resource.postValue(Resource.Success(it))
            }
        } catch (e: HttpException) {
            val parsedError = try {
                readServerError(e)
            } catch (e: Exception) {
                Exception(e)
            }
            resource.value = Resource.Error(parsedError)
        } catch (e: Exception) {
            resource.value = Resource.Error(e)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): LiveData<R>
}