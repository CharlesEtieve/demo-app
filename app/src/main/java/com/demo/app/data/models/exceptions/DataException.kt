package com.demo.app.data.models.exceptions

import com.demo.app.data.models.domainMappingProtocols.DomainExceptionConvertible
import com.demo.app.data.models.response.error.ErrorResponse
import com.demo.app.domain.models.DomainException
import com.demo.app.domain.models.DomainNetworkException
import com.demo.app.domain.models.errors.DomainError
import java.net.HttpURLConnection

private const val HTTP_UNPROCESSABLE_ENTITY = 422

/**
 * Enum that represent all DataException that could be fired from data layer to domain layer
 */
sealed class DataException : Exception(), DomainExceptionConvertible
data class DataNetworkException(val code: Int, val errorResponse: ErrorResponse) : DataException() {
    override val message: String = errorResponse.error

    override fun toDomain(): DomainException = when (code) {
        HttpURLConnection.HTTP_BAD_REQUEST -> DomainNetworkException.BadRequest(errorResponse.toDomain())
        HttpURLConnection.HTTP_UNAUTHORIZED -> DomainNetworkException.Unauthorized(errorResponse.toDomain())
        HttpURLConnection.HTTP_FORBIDDEN -> DomainNetworkException.Forbidden(errorResponse.toDomain())
        HttpURLConnection.HTTP_NOT_FOUND -> DomainNetworkException.NotFound(errorResponse.toDomain())
        HttpURLConnection.HTTP_CONFLICT -> DomainNetworkException.Conflict(errorResponse.toDomain())
        HTTP_UNPROCESSABLE_ENTITY -> DomainNetworkException.UnprocessableEntity(errorResponse.toDomain())
        else -> DomainNetworkException.InternalError(errorResponse.toDomain())
    }
}
data class DataAPIDecodeException(override val message: String) : DataException() {
    override fun toDomain(): DomainException =
        DomainNetworkException.InternalError(DomainError((message)))
}