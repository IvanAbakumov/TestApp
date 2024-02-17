package com.world.domain.common


sealed class BaseException(val error: String = "") : Throwable()

class ApiErrorException(val apiError: ApiError) : BaseException(apiError.error.toString()) {
    //fun getPhoneNumberError() = apiError.fieldsError?.get("phoneNumber")?.firstOrNull()
}

class UnauthorizedException : BaseException()
class NotValidFlow : BaseException()
class NullPointerException : BaseException()
class ConnectivityError() : BaseException("The network connection seems to be down")
class UnknownException : BaseException()

open class ApiError(
    val error: String? = null,
    val fieldsError: HashMap<String, List<String>>?,
    val detail: String? = null,
)

open class Violations(
    val propertyPath: String?,
    val title: String?
) {
    override fun toString(): String {
        return title ?: "Api error"
    }
}