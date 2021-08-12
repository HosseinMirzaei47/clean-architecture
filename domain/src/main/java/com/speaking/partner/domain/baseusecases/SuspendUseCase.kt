package com.speaking.partner.domain.baseusecases

import com.part.livetaskcore.usecases.ParametricUseCase
import com.speaking.partner.domain.utils.readServerError
import com.speaking.partner.shared.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * Executes business logic synchronously or asynchronously using Coroutines.
 *
 * The [execute] method of [SuspendUseCase] is a suspend function as opposed to the
 * [SuspendUseCase.execute] method of [UseCase].
 */
abstract class SuspendUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) :
    ParametricUseCase<P, Resource<R>> {

    /** Executes the use case asynchronously and returns a [Resource].
     *
     * @return a [Resource].
     *
     * @param parameters the input parameters to run the use case with
     */
    override suspend operator fun invoke(parameter: P): Resource<R> {
        return try {
            // Moving all use case's executions to the injected dispatcher
            // In production code, this is usually the Default dispatcher (background thread)
            // In tests, this becomes a TestCoroutineDispatcher
            withContext(coroutineDispatcher) {
                execute(parameter).let {
                    Resource.Success(it)
                }
            }
        } catch (e: HttpException) {
            val parsedError = try {
                readServerError(e)
            } catch (e: Exception) {
                Exception(e)
            }
            Resource.Error(parsedError)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}
