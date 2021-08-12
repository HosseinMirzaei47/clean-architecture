package com.speaking.partner.data.utils

import com.speaking.partner.shared.exceptions.ServerException
import retrofit2.HttpException
import retrofit2.Response

@Throws(ServerException::class)
fun <T> Response<T>.bodyOrThrow(): T {
    if (!isSuccessful) throw HttpException(this)
    return body()!!
}