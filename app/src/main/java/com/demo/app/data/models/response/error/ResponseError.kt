package com.demo.app.data.models.response.error

import com.demo.app.data.models.domainMappingProtocols.DomainModelConvertible
import com.demo.app.domain.models.errors.DomainError
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: String,
    val data: Map<String, String>? = null
) : DomainModelConvertible<DomainError> {

    override fun toDomain(): DomainError =
        DomainError(error, data)
}
