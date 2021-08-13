package com.app.core.data.utils

import com.app.core.shared.exceptions.ServerException
import retrofit2.HttpException
import retrofit2.Response

@Throws(ServerException::class)
fun <T> Response<T>.bodyOrThrow(): T {
    if (!isSuccessful) throw HttpException(this)
    return body()!!
}