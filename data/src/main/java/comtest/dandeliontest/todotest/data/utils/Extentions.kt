package comtest.dandeliontest.todotest.data.utils

import comtest.dandeliontest.todotest.shared.exceptions.ServerException
import retrofit2.HttpException
import retrofit2.Response

@Throws(ServerException::class)
fun <T> Response<T>.bodyOrThrow(): T {
    if (!isSuccessful) throw HttpException(this)
    return body()!!
}