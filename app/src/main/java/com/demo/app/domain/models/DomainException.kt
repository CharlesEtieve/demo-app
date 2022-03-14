package com.demo.app.domain.models

import com.demo.app.domain.models.errors.DomainError

/**
 * Enum used to define all DomainExceptions used in Domain. The conversion is made from the Data layer
 */
sealed class DomainException : Exception()
sealed class DomainNetworkException(open val domainError: DomainError) : DomainException() {
    data class BadRequest(override val domainError: DomainError) : DomainNetworkException(domainError)
    data class Unauthorized(override val domainError: DomainError) : DomainNetworkException(domainError)
    data class Forbidden(override val domainError: DomainError) : DomainNetworkException(domainError)
    data class NotFound(override val domainError: DomainError) : DomainNetworkException(domainError)
    data class Conflict(override val domainError: DomainError) : DomainNetworkException(domainError)
    data class UnprocessableEntity(override val domainError: DomainError) : DomainNetworkException(domainError)
    data class InternalError(override val domainError: DomainError) : DomainNetworkException(domainError)
}
