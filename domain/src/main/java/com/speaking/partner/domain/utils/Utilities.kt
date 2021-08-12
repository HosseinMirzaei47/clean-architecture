package com.speaking.partner.domain.utils

import com.speaking.partner.shared.exceptions.ServerException
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException

fun readServerError(exception: HttpException): ServerException {
    val errorString = exception.response()?.errorBody()?.string()
        ?: return ServerException(null, exception.code())

    val errorBodyObject = JSONObject(errorString)
    val errorData = errorBodyObject.getJSONObject("data")
    var errorMessage: String? = null

    if (!errorData.isNull("additionalInfo")) {
        val additionalInfo = errorData.get("additionalInfo")
        errorMessage = when (additionalInfo) {
            is JSONArray -> {
                additionalInfo.getJSONObject(0).getString("message")
            }
            is JSONObject -> {
                if (additionalInfo.has("message")) additionalInfo.getString("message") else null
            }
            else -> null
        }
    }
    if (errorMessage == null) {
        errorMessage = when {
            errorData.get("message") is String -> {
                errorData.getString("message")
            }
            errorData.get("message") is JSONObject -> {
                errorData.getJSONObject("message").getString("fa")
            }
            else -> null
        }
    }

    return ServerException(errorMessage, exception.code())
}