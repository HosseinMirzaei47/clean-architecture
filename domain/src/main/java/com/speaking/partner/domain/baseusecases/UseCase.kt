package com.speaking.partner.domain.baseusecases

import com.part.livetaskcore.usecases.ParametricUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Executes business logic synchronously or asynchronously using Coroutines.
 */
abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) :
    ParametricUseCase<P, com.speaking.partner.shared.resource.Resource<R>> {

    /** Executes the use case asynchronously and returns a [Result].
     *
     * @return a [Result].
     *
     * @param parameter the input parameters to run the use case with
     */
    override suspend operator fun invoke(parameter: P): com.speaking.partner.shared.resource.Resource<R> {
        return try {
            // Moving all use case's executions to the injected dispatcher
            // In production code, this is usually the Default dispatcher (background thread)
            // In tests, this becomes a TestCoroutineDispatcher
            withContext(coroutineDispatcher) {
                execute(parameter).let {
                    com.speaking.partner.shared.resource.Resource.Success(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            com.speaking.partner.shared.resource.Resource.Error(e)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}