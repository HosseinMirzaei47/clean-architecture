package com.speaking.partner.shared.exceptions

class ServerException(message: String?, val code: Int?) : Exception(message)
