package com.app.core.shared.exceptions

class ServerException(message: String?, val code: Int?) : Exception(message)
