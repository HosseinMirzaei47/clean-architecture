package comtest.dandeliontest.todotest.shared.exceptions

class ServerException(message: String?, val code: Int?) : Exception(message)
