package com.demo.app.data.models.domainMappingProtocols

import com.demo.app.domain.models.DomainException

/**
 * Interface used to implement toDomainExceptionType for Exceptions triggered from the Data
 */
interface DomainExceptionConvertible {
    fun toDomain(): DomainException
}
